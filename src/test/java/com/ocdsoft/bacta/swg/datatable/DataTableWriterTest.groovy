package com.ocdsoft.bacta.swg.datatable

import bacta.iff.Iff
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by kyle on 5/7/2016.
 */
class DataTableWriterTest extends Specification {

    @Unroll
    def "LoadFromSpreadsheet"() {

        setup:
        def dataTableWriter = new DataTableWriter()
        def fileUrl = this.class.getResource(resourceIn)
        def file = new File(fileUrl.toURI())
        def actualIff = new Iff(1024)
        def expectedIff = new Iff("reserved.iff", this.class.getResource(resourceOut).getBytes())

        when:
        dataTableWriter.loadFromSpreadsheet(file.getAbsolutePath())
        dataTableWriter.save(actualIff)

        then:
        Arrays.equals(
                Arrays.copyOf(expectedIff.rawData, expectedIff.getCurrentLength()),
                Arrays.copyOf(actualIff.rawData, expectedIff.getCurrentLength()))
        noExceptionThrown()

        where:
        resourceIn      | resourceOut
        "/reserved.tab" | "/reserved.iff"

    }
}
