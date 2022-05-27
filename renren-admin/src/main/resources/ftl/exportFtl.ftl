<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook
        xmlns="urn:schemas-microsoft-com:office:spreadsheet"
        xmlns:o="urn:schemas-microsoft-com:office:office"
        xmlns:x="urn:schemas-microsoft-com:office:excel"
        xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
        xmlns:html="http://www.w3.org/TR/REC-html40"
        xmlns:dt="uuid:C2F41010-65B3-11d1-A29F-00AA00C14882">
    <DocumentProperties
            xmlns="urn:schemas-microsoft-com:office:office">
        <LastAuthor>Administrator</LastAuthor>
        <Created>2022-04-10T07:23:25Z</Created>
        <LastSaved>2022-04-10T17:03:10Z</LastSaved>
    </DocumentProperties>
    <CustomDocumentProperties
            xmlns="urn:schemas-microsoft-com:office:office">
        <ICV dt:dt="string">E1A171980BE0425D998C1475C1228A76</ICV>
        <KSOProductBuildVer dt:dt="string">2052-11.1.0.11411</KSOProductBuildVer>
    </CustomDocumentProperties>
    <ExcelWorkbook
            xmlns="urn:schemas-microsoft-com:office:excel">
        <WindowWidth>23325</WindowWidth>
        <WindowHeight>9750</WindowHeight>
        <ProtectStructure>False</ProtectStructure>
        <ProtectWindows>False</ProtectWindows>
    </ExcelWorkbook>
    <Styles>
        <Style ss:ID="Default" ss:Name="Normal">
            <Alignment ss:Vertical="Center"/>
            <Borders/>
            <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#000000"/>
            <Interior/>
            <NumberFormat/> <Protection/>
        </Style>
        <Style ss:ID="s49"/>
    </Styles>
    <Worksheet ss:Name="Sheet1">
        <Table ss:ExpandedColumnCount="8" ss:ExpandedRowCount="2" x:FullColumns="1" x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="13.5">
            <Column ss:Index="1" ss:StyleID="Default" ss:AutoFitWidth="0" ss:Width="62.25" ss:Span="6"/>
            <Column ss:Index="8" ss:StyleID="Default" ss:AutoFitWidth="0" ss:Width="66.75"/>
            <Row>
                <Cell>
                    <Data ss:Type="String">部门名称</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">录著数</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">借阅数</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">归还数</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">转出数</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">转入数</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">交付数</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">修改数</Data>
                </Cell>
            </Row>
            <#list data as list>
                <Row>
                    <Cell>
                        <Data ss:Type="String">${list.name}</Data>
                    </Cell>
                    <Cell>
                        <Data ss:Type="String">${list.num1}</Data>
                    </Cell>
                    <Cell>
                        <Data ss:Type="String">${list.num2}</Data>
                    </Cell>
                    <Cell>
                        <Data ss:Type="String">${list.num3}</Data>
                    </Cell>
                    <Cell>
                        <Data ss:Type="String">${list.num4}</Data>
                    </Cell>
                    <Cell>
                        <Data ss:Type="String">${list.num5}</Data>
                    </Cell>
                    <Cell>
                        <Data ss:Type="String">${list.num6}</Data>
                    </Cell>
                    <Cell>
                        <Data ss:Type="String">${list.num7}</Data>
                    </Cell>
                </Row>
            </#list>
        </Table>
        <WorksheetOptions
                xmlns="urn:schemas-microsoft-com:office:excel">
            <PageSetup>
                <Header x:Margin="0.3"/>
                <Footer x:Margin="0.3"/>
                <PageMargins x:Left="0.7" x:Right="0.7" x:Top="0.75" x:Bottom="0.75"/>
            </PageSetup>
            <Selected/>
            <TopRowVisible>0</TopRowVisible>
            <LeftColumnVisible>0</LeftColumnVisible>
            <PageBreakZoom>100</PageBreakZoom>
            <Panes>
                <Pane>
                    <Number>3</Number>
                    <ActiveRow>8</ActiveRow>
                    <ActiveCol>9</ActiveCol>
                    <RangeSelection>R9C10</RangeSelection>
                </Pane>
            </Panes>
            <ProtectObjects>False</ProtectObjects>
            <ProtectScenarios>False</ProtectScenarios>
        </WorksheetOptions>
    </Worksheet>
</Workbook>