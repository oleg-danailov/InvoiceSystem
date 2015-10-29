<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tax="http://taxservice.estafet.com/">
    <xsl:output indent="yes" method="xml"/>
    <xsl:template match="/">
        <tax:taxRequest>
            <invoiceType>
                <xsl:for-each select="inv:invoiceRequest" xmlns:inv="http://invoiceservice.estafet.com/">
                        <xsl:value-of select="invoiceType"/>
                </xsl:for-each>
            </invoiceType>
        </tax:taxRequest>
    </xsl:template>
</xsl:stylesheet>