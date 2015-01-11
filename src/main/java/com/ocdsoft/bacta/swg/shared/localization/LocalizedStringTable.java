package com.ocdsoft.bacta.swg.shared.localization;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.util.CharsetUtil;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class LocalizedStringTable {
    public static final int ABCD = 0xABCD;

    public String filename;
    @Getter
    public TIntObjectMap<StringEntry> stringTable;

    public LocalizedStringTable(ByteBuffer buffer, String filename) {
        if (buffer.remaining() <= 4)
            return;

        this.filename = filename;

        if (buffer.order() != ByteOrder.LITTLE_ENDIAN)
            buffer.order(ByteOrder.LITTLE_ENDIAN);

        int header = buffer.getInt();
        byte unknownFlag = buffer.get();
        int indexCounter = buffer.getInt();
        int listSize = buffer.getInt();

        stringTable = new TIntObjectHashMap<StringEntry>(listSize);

        for (int i = 0; i < listSize; i++) {
            int index = buffer.getInt();
            int unk1 = buffer.getInt();
            int size = buffer.getInt() * 2;

            stringTable.put(index, new StringEntry(
                    new String(buffer.array(), buffer.position(), size, CharsetUtil.UTF_16LE)));
            buffer.position(buffer.position() + size);
        }

        for (int i = 0; i < listSize; i++) {
            int index = buffer.getInt();
            int size = buffer.getInt();

            StringId stringId = new StringId(filename, new String(buffer.array(), buffer.position(), size, CharsetUtil.US_ASCII));
            buffer.position(buffer.position() + size);

            stringTable.get(index).setStringId(stringId);
        }

    }

//	public static void main(String[] args) throws Exception {
//		TreeArchive archive = new TreeArchive();
//		archive.processLiveFile("C:\\Users\\crush\\Downloads\\client\\", "swgemu_live.cfg");
//		
//		List<String> files = archive.getFileListInDirectory("terrain");
//		
//		for (String file : files) {
//			if (!file.contains("space") || !file.endsWith(".trn"))
//				continue;
//			
//			System.out.println(file);
//			IffFile iff = new IffFile(archive.get(file));
//			iff.printChunkRecursively(iff.getRootChunk(), 1);
//			System.out.println();
//		}
//		
//		
//		
//		List<String> files = archive.getFileListInDirectory("string/en/");
//		
//		PrintWriter output = new PrintWriter(new FileWriter("C:\\xampp\\htdocs\\swgemuconvo\\inc\\stf-files.js"));
//		
//		output.println("window.stfFiles = [");
//		
//		for (String file : files) {
//			output.println("\t'" + file.substring(10) + "',");
//			
//			LocalizedStringTable stf = new LocalizedStringTable(archive.get(file).getBytes(), file.substring(10));
//			
//			File f = new File("C:\\xampp\\htdocs\\swgemuconvo\\stf\\" + file.substring(10, file.length() - 4) + ".json");
//			Files.createDirectories(f.toPath().getParent());
//			
//			PrintWriter output2 = new PrintWriter(new FileWriter(f));
//			output2.println("{");
//			output2.println("\t\"stfFile\":\"" + file.substring(10, file.length() - 4) + "\",");
//			output2.println("\t\"entries\": {");
//			if (stf.getStringTable() != null) {
//				String[] entries = new String[stf.getStringTable().keys().length];
//				int i = 0;
//				for (int key : stf.getStringTable().keys()) {
//					StringEntry entry = stf.getStringTable().get(key);
//					entries[i++] = String.format("\t\t\"%s\":\"%s\"", entry.getStringId().getKey(), entry.getValue());
//				}
//				output2.println(Joiner.on(",").join(entries));
//			}
//			output2.println("}}");
//			output2.close();
//		}
//		
//		output.println("];");
//		output.close();
//	}
}
