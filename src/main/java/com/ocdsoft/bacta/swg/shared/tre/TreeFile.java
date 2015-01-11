package com.ocdsoft.bacta.swg.shared.tre;

import com.google.inject.Singleton;
import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZStream;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * Created by crush on 3/19/14.
 * <p>
 * A Tree Archive (*.tre) is an archive format seen in Star Wars Galaxies, and potentially other games. It is unknown
 * if this is an SOE proprietary format, or a common format with little, to no documentation available to the public
 * domain.
 * </p>
 * <h3>File Structure</h3>
 * <p>
 * TreeFile's contain Zlib compressed files in the following archive format.
 * <pre>
 * TreeFile:Header {
 *     INT32 fileId
 *     INT32 version
 *     INT32 totalRecords
 *     INT32 recordsOffset
 *     INT32 recordsCompressionLevel
 *     INT32 recordsDeflatedSize
 *     INT32 namesCompressionLevel
 *     INT32 namesDeflatedSize
 *     INT32 namesInflatedSize
 * }
 *
 * TreeFile:Data {
 *     BYTE[] dataBlock
 *     BYTE[] recordBlock
 *     BYTE[] namesBlock
 *     BYTE[] checksumBlock
 * }
 * </pre>
 * The data section comes after the file header section, and can be broken down into 4 blocks:
 * <ul>
 * <li><code>dataBlock</code> - This is the raw data of a file archived within this TreeFile.</li>
 * <li><code>recordBlock</code> - This is a {@link SearchTree.TreeRecordInfo}. It contains information about
 * each file contained within the archive.</li>
 * <li><code>namesBlock</code> - This is a block of ASCII names for each file. It contains the entire path of the file,
 * delimited by forward slash.</li>
 * <li><code>checksumBlock</code> - This is a block of MD5 checksums, used to validate the integrity of the data in this
 * archive, linked to each file. This is used in the file scan portion of the SOE launcher to see if TREE data
 * should be retrieved.
 * </ul>
 * </p>
 * <p>
 * This TreeFile class only assembles and stashes the {@link SearchTree.TreeRecordInfo} information, with pointers to the data
 * for the referenced file. This allows the TreeFile to be parsed extremely quickly, a directory to be created, and
 * for individual files to be retrieved from the relevant archive when desired.
 * </p>
 */
@SuppressWarnings("deprecation")
@Singleton
public class TreeFile {
    public static final int ID_TREE = 0x54524545; //'TREE'
    public static final int ID_0005 = 0x30303035; //'0005'
    public static final int ID_0006 = 0x30303036; //'0006'

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Collection<SearchNode> nodes = new ArrayList<>();

    @Getter
    @Setter
    private String rootPath;

    @SuppressWarnings("unchecked")
    public void addSearchPath(String filePath, int priority) {
        nodes.add(new SearchPath(filePath, priority));
        Collections.sort((ArrayList) nodes);
    }

    @SuppressWarnings("unchecked")
    public void addSearchTree(String filePath, int priority) {
        nodes.add(new SearchTree(filePath, priority));
        Collections.sort((ArrayList) nodes);
    }

    @SuppressWarnings("unchecked")
    public void addSearchTOC(String filePath, int priority) {
        nodes.add(new SearchTOC(filePath, priority));
        Collections.sort((ArrayList) nodes);
    }

    @SuppressWarnings("unchecked")
    public void addSearchAbsolute(int priority) {
        nodes.add(new SearchAbsolute(this.getRootPath(), priority));
        Collections.sort((ArrayList) nodes);
    }

    @SuppressWarnings("unchecked")
    public void addSearchCache(int priority) {
        nodes.add(new SearchCache(priority));
        Collections.sort((ArrayList) nodes);
    }

    public boolean exists(final String filePath) {
        for (SearchNode node : nodes) {
            if (node.exists(filePath))
                return true;
        }

        return false;
    }

    /**
     * Attempts to open the file at the desired path if it exists.
     * <code>
     * TreeFile treeFile = ...;
     * byte[] bytes = treeFile.open("/object/base/shared_base_object.iff");
     * </code>
     *
     * @param filePath The path of the file that should be opened.
     * @return Returns a byte array representing the file, or null if the file didn't exist.
     */
    public byte[] open(final String filePath) {

        byte[] bytes = null;

        for (SearchNode searchNode : nodes) {
            bytes = searchNode.open(filePath);

            if (bytes != null)
                break;
        }

        return bytes;
    }

    /**
     * Gets a listing of all the files in the specified directory. This function will recurse through subdirectories.
     *
     * @param directory The directory to begin searching for files.
     * @return A set of all the paths in the directory.
     */
    public Set<String> listFiles(final String directory) {
        final Set<String> set = new TreeSet<>();

        for (final SearchNode node : nodes) {
            if (node instanceof SearchTree) {
                final SearchTree searchTree = (SearchTree) node;
                final Set<String> files = searchTree.listFiles();

                for (String file : files) {
                    if (file.startsWith(directory)) {
                        set.add(file);
                    }
                }
            }
        }

        return set;
    }

    public static void inflate(ByteBuffer buffer, ByteBuffer dst, int compressionLevel, int deflatedSize) {
        byte[] src = new byte[deflatedSize];
        buffer.get(src);

        //TODO can we optimize this somehow?
        if (compressionLevel == 0) {
            dst.put(src);
            dst.rewind();
            return;
        }

        ZStream zstream = new ZStream();
        zstream.avail_in = 0;
        zstream.inflateInit();
        zstream.next_in = src;
        zstream.next_in_index = 0;
        zstream.avail_in = src.length;
        zstream.next_out = dst.array();
        zstream.next_out_index = 0;
        zstream.avail_out = dst.array().length;
        zstream.inflate(JZlib.Z_FINISH);
        zstream.inflateEnd();
    }
}
