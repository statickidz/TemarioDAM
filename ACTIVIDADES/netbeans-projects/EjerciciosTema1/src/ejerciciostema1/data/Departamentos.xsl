<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html><xsl:apply-templates /></html>
    </xsl:template>
    <xsl:template match="departamentos">
        <head>
            <title>Listado de Departamentos</title>
            <link href="http://fonts.googleapis.com/css?family=Roboto:100,500,300,700,400" rel="stylesheet" />
            <style type="text/css">
                  body {
                    background: #dedede;
                    font-family: Roboto, sans-serif;
                  }

                  * {
                    box-sizing: border-box;
                  }

                  .aa_h2 {
                    font: 100 5rem/1 Roboto;
                    text-transform: uppercase;
                  }

                  table {
                    background: #fff;
                  }

                  table, thead, tbody, tfoot, tr, td, th {
                    text-align: center;
                    margin: auto;
                    border: 1px solid #dedede;
                    padding: 1rem;
                    width: 50%;
                  }

                  .table {
                    display: table;
                    width: 50%;
                  }

                  .tr {
                    display: table-row;
                  }

                  .thead {
                    display: table-header-group;
                  }

                  .tbody {
                    display: table-row-group;
                  }

                  .tfoot {
                    display: table-footer-group;
                  }

                  .col {
                    display: table-column;
                  }

                  .colgroup {
                    display: table-column-group;
                  }

                  .td, .th {
                    display: table-cell;
                    width: 50%;
                  }

                  .caption {
                    display: table-caption;
                  }

                  .table,
                  .thead,
                  .tbody,
                  .tfoot,
                  .tr,
                  .td,
                  .th {
                    text-align: center;
                    margin: auto;
                    padding: 1rem;
                  }

                  .table {
                    background: #fff;
                    margin: auto;
                    border: none;
                    padding: 0;
                    margin-bottom: 5rem;
                  }

                  .th {
                    font-weight: 700;
                    border: 1px solid #dedede;
                  }
                  .th:nth-child(odd) {
                    border-right: none;
                  }

                  .td {
                    font-weight: 300;
                    border: 1px solid #dedede;
                    border-top: none;
                  }
                  .td:nth-child(odd) {
                    border-right: none;
                  }

                  .aa_htmlTable {
                    background: tomato;
                    padding: 5rem;
                    display: table;
                    width: 100%;
                    height: 100vh;
                    vertical-align: middle;
                  }

                  .aa_css {
                    background: skyblue;
                    padding: 5rem;
                    display: table;
                    width: 100%;
                    height: 100vh;
                    vertical-align: middle;
                  }

                  .aa_ahmadawais {
                    display: table;
                    width: 100%;
                    font: 100 1.2rem/2 Roboto;
                    margin: 5rem auto;
                  }
            </style>
        </head>
        <body>
            <div class="aa_htmlTable">
                <h2 class="aa_h2">Listado de Departamentos</h2>
                <table>
                    <thead>
                        <tr>
                            <th>Nombre</th>
                            <th>Localidad</th>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:apply-templates select="departamento" />
                    </tbody>
                </table>
            </div>
        </body>
    </xsl:template>
    <xsl:template match="departamento">
        <tr><xsl:apply-templates /></tr>
    </xsl:template>
    <xsl:template match="nombre|localidad">
        <td><xsl:apply-templates /></td>
    </xsl:template>
</xsl:stylesheet>