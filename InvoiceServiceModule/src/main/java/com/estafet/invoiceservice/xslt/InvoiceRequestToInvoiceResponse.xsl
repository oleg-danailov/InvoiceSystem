<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:inv="http://invoiceservice.estafet.com/" >
    <xsl:output indent="yes" method="xml"/>
    <xsl:template match="/">
        <inv:invoiceRequestResponse xmlns:inv="http://invoiceservice.estafet.com/">
        <xsl:for-each select="inv:invoiceRequest">
            <invoiceAmount><xsl:value-of select="invoiceAmount"/></invoiceAmount>
            <invoiceId><xsl:value-of select="invoiceId"/></invoiceId>
            <invoiceNumber><xsl:value-of select="invoiceNumber"/></invoiceNumber>
            <invoiceType><xsl:value-of select="invoiceType"/></invoiceType>
            <providerCompany><xsl:value-of select="providerCompany"/></providerCompany>
            <receiverCompany><xsl:value-of select="receiverCompany"/></receiverCompany>
            <taxesAmount><xsl:value-of select="taxesAmount"/></taxesAmount>
            <totalAmount><xsl:value-of select="totalAmount"/></totalAmount>
        </xsl:for-each>
        </inv:invoiceRequestResponse>
    </xsl:template>
</xsl:stylesheet>