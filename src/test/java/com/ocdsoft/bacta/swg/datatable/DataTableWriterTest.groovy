package com.ocdsoft.bacta.swg.datatable

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
        def fileUrl = this.class.getResource(resourceLocation)
        def file = new File(fileUrl.toURI())

        when:
        dataTableWriter.loadFromSpreadsheet(file.getAbsolutePath())
        dataTableWriter.save(false)

        then:
        noExceptionThrown()

        where:
        resourceLocation | something
        "/reserved.tab"  |   0

    }
}
