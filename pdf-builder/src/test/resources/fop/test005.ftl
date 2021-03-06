<?xml version="1.0" encoding="UTF-8"?> 
<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" 
         xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
         xmlns:fox="http://xmlgraphics.apache.org/fop/extensions"
         xmlns:svg="http://www.w3.org/2000/svg"
         font-family="'Source Sans Pro', Arial" text-align="left" font-size="11pt">
    
    <fo:layout-master-set>
        <fo:simple-page-master master-name="a4-portrait-cover"
                               page-width="210mm" 
                               page-height="297mm">
            <fo:region-body margin-top="66mm" margin-left="20mm" margin-right="20mm" margin-bottom="20mm"/>
            <fo:region-before extent="12mm"/>
            <fo:region-after extent="12mm"/>
            <fo:region-start extent="12mm"/>
            <fo:region-end extent="12mm"/>
        </fo:simple-page-master>
        <fo:simple-page-master master-name="a4-portrait-page"
            page-width="210mm" 
            page-height="297mm">
            <fo:region-body margin-top="50mm" margin-left="20mm" margin-right="20mm" margin-bottom="20mm"/>
            <fo:region-before extent="12mm"/>
            <fo:region-after extent="12mm"/>
            <fo:region-start extent="12mm"/>
            <fo:region-end extent="12mm"/>
        </fo:simple-page-master>
        
    </fo:layout-master-set>
    
    <fo:page-sequence master-reference="a4-portrait-page">

        <fo:static-content flow-name="xsl-region-start">
            <fo:block-container position="absolute" top="0mm" left="0mm" >
                <fo:block>
                    <fo:external-graphic src="/fop/border.svg"></fo:external-graphic>
                </fo:block>
            </fo:block-container> 
            <fo:block-container position="absolute" top="281mm" left="16mm">
                <fo:block color="white">
                ${markupUtils.process(dictionary["gdar.general.footerurl"])}
                </fo:block>
            </fo:block-container>
            <fo:block-container position="absolute" top="0mm" left="0mm" >
                <fo:block>
                    <fo:external-graphic src="/fop/title-page.svg"></fo:external-graphic>
                </fo:block>
            </fo:block-container> 
            <fo:block-container  position="absolute" top="18mm" left="16mm" width="150mm">
                <fo:block font-family="Aharoni" font-size="23pt" color="${dictionary["style.green"]}" font-weight="bold">
                    Green Deal Advice Report
                </fo:block>
            </fo:block-container>
            <fo:block-container  position="absolute" top="27.5mm" left="16mm" width="150mm">
                <fo:block font-family="Century Gothic" font-size="10">
                    Reference: ${rrn}
                </fo:block>
            </fo:block-container>
        </fo:static-content>
        
        <fo:flow flow-name="xsl-region-body">
        
            <fo:block-container>
                <fo:table>
                    <fo:table-header>
                        <fo:table-row>
                            <fo:table-cell>
                                <fo:block margin-top="20mm"></fo:block>
                                <fo:block-container position="absolute" top="-3mm" left="82mm">
                                    <fo:block>
                                        <fo:external-graphic height="35mm" width="35mm" src="/fop/arrow-01.svg"></fo:external-graphic>
                                    </fo:block>
                                </fo:block-container>
                                <fo:block-container position="absolute" top="-3mm" left="112mm">
                                    <fo:block>
                                        <fo:external-graphic height="35mm" width="35mm" src="/fop/arrow-02.svg"></fo:external-graphic>
                                    </fo:block>
                                </fo:block-container>
                                <fo:block-container position="absolute" top="-3mm" left="142mm">
                                    <fo:block>
                                        <fo:external-graphic height="35mm" width="35mm" src="/fop/arrow-03.svg"></fo:external-graphic>
                                    </fo:block>
                                </fo:block-container>
                            </fo:table-cell>
                        </fo:table-row>
                    </fo:table-header>
                    <fo:table-footer>
                        <fo:table-row>
                            <fo:table-cell>
                                <fo:block height="30mm">
                                    <fo:retrieve-table-marker retrieve-class-name="table-1-footer" retrieve-position-within-table="last-ending"/>
                                </fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                    </fo:table-footer>
                    <fo:table-body>
                        
                        <!-- Solid Wall Insulation - Start -->
                        <fo:table-row>
                            <fo:table-cell>
                                <fo:block-container keep-together.within-page="always">
                                    <fo:table>
                                        <fo:table-column column-width="170mm"/>
                                        <fo:table-body>
                                            <!-- -->
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block-container width="82mm">
                                                        <fo:block border-style="solid" border-width="0.5mm" border-color="green" fox:border-radius="3mm" background-color="green">
                                                            <fo:table>
                                                                <fo:table-column column-width="82mm"/>
                                                                <fo:table-body>
                                                                    <fo:table-row margin="3mm">
                                                                        <fo:table-cell>
                                                                            <fo:block padding-before="3mm" padding-after="3mm" color="white" font-weight="bold">
                                                                                Solid wall insulation
                                                                            </fo:block>
                                                                            <fo:block>
                                                                                <fo:marker marker-class-name="table-1-footer">
                                                                                    A
                                                                                    <!--  
                                                                                    <fo:block>
                                                                                        <fo:external-graphic src="continued.svg"></fo:external-graphic>
                                                                                    </fo:block>
                                                                                    -->
                                                                                </fo:marker>
                                                                            </fo:block>
                                                                        </fo:table-cell>
                                                                    </fo:table-row>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </fo:block>
                                                    </fo:block-container>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <!-- -->
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block-container>
                                                        <fo:block border-style="solid" border-width="0.5mm" border-color="green" fox:border-radius="3mm">
                                                            <fo:table>
                                                                <fo:table-column column-width="80mm"/>
                                                                <fo:table-column column-width="30mm"/>
                                                                <fo:table-column column-width="30mm"/>
                                                                <fo:table-column column-width="30mm"/>
                                                                <fo:table-body>
                                                                    <fo:table-row margin="3mm">
                                                                        <fo:table-cell border-right-style="solid" border-right-width="0.25mm" border-right-color="green" padding-before="3mm" padding-after="3mm">
                                                                            <fo:block>
                                                                                <fo:list-block>
                                                                                    <fo:list-item>
                                                                                        <fo:list-item-label margin-left="2mm">
                                                                                            <fo:block>
                                                                                                <fo:character character="&#x2022;"/>
                                                                                            </fo:block>
                                                                                        </fo:list-item-label>
                                                                                        <fo:list-item-body margin-left="2mm" padding-left="5mm">
                                                                                            <fo:block keep-together.within-page="always" text-align="justify">
                                                                                                Cladding that's fixed on to the outside walls of your building
                                                                                            </fo:block>
                                                                                        </fo:list-item-body>
                                                                                    </fo:list-item>                                                        
                                                                                    <fo:list-item>
                                                                                        <fo:list-item-label margin-left="2mm">
                                                                                            <fo:block>
                                                                                                <fo:character character="&#x2022;"/>
                                                                                            </fo:block>
                                                                                        </fo:list-item-label>
                                                                                        <fo:list-item-body margin-left="2mm" padding-left="5mm">
                                                                                            <fo:block keep-together.within-page="always" text-align="justify">
                                                                                                Helps stop heat escaping through your walls
                                                                                            </fo:block>
                                                                                        </fo:list-item-body>
                                                                                    </fo:list-item>                                                        
                                                                                    <fo:list-item>
                                                                                        <fo:list-item-label margin-left="2mm">
                                                                                            <fo:block>
                                                                                                <fo:character character="&#x2022;"/>
                                                                                            </fo:block>
                                                                                        </fo:list-item-label>
                                                                                        <fo:list-item-body margin-left="2mm" padding-left="5mm">
                                                                                            <fo:block keep-together.within-page="always" text-align="justify">
                                                                                                Helps you use less energy to keep your home cosy
                                                                                            </fo:block>
                                                                                        </fo:list-item-body>
                                                                                    </fo:list-item>                                                        
                                                                                </fo:list-block>
                                                                            </fo:block>
                                                                        </fo:table-cell>
                                                                        <fo:table-cell border-right-style="solid" border-right-width="0.25mm" border-right-color="green" padding-before="3mm" padding-after="3mm">
                                                                            <fo:block text-align="center">
                                                                                £xx
                                                                            </fo:block>
                                                                        </fo:table-cell>
                                                                        <fo:table-cell border-right-style="solid" border-right-width="0.25mm" border-right-color="green" padding-before="3mm" padding-after="3mm">
                                                                            <fo:block text-align="center">
                                                                                £xx to £yy
                                                                            </fo:block>
                                                                        </fo:table-cell>
                                                                        <fo:table-cell padding-before="3mm" padding-after="3mm">
                                                                            <fo:block-container >
                                                                                <fo:block padding-bottom="2mm">
                                                                                    Green Deal loan
                                                                                </fo:block>
                                                                                <fo:block padding-bottom="2mm">
                                                                                    Energy Company Obligation
                                                                                </fo:block>
                                                                            </fo:block-container>
                                                                        </fo:table-cell>
                                                                    </fo:table-row>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </fo:block>
                                                    </fo:block-container>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <!-- -->
                                        </fo:table-body>
                                    </fo:table>
                                    <fo:block-container position="absolute" top="-7mm" left="60mm">
                                        <fo:block>
                                            <fo:external-graphic height="24mm" width="24mm" src="/fop/solid_wall_insulation.svg"></fo:external-graphic>
                                        </fo:block>
                                    </fo:block-container>
                                </fo:block-container>
                            </fo:table-cell>
                        </fo:table-row>
                        <!-- Solid Wall Insulation - End -->
                        
                        <!-- PV - Start -->
                        <fo:table-row>
                            <fo:table-cell>
                                <fo:block-container keep-together.within-page="always">
                                    <fo:table>
                                        <fo:table-column column-width="170mm"/>
                                        <fo:table-body>
                                            <!-- -->
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block-container width="82mm">
                                                        <fo:block border-style="solid" border-width="0.5mm" border-color="green" fox:border-radius="3mm" background-color="green">
                                                            <fo:table>
                                                                <fo:table-column column-width="82mm"/>
                                                                <fo:table-body>
                                                                    <fo:table-row margin="3mm">
                                                                        <fo:table-cell>
                                                                            <fo:block padding-before="3mm" padding-after="3mm" color="white" font-weight="bold" margin-right="14mm">
                                                                                Solar electric panels – known as PV panels
                                                                            </fo:block>
                                                                            <fo:block>
                                                                                <fo:marker marker-class-name="table-1-footer">
                                                                                B
                                                                                    <!-- <fo:external-graphic src="continued.svg"></fo:external-graphic> -->
                                                                                </fo:marker>
                                                                            </fo:block>
                                                                        </fo:table-cell>
                                                                    </fo:table-row>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </fo:block>
                                                    </fo:block-container>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <!-- -->
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block-container>
                                                        <fo:block border-style="solid" border-width="0.5mm" border-color="green" fox:border-radius="3mm">
                                                            <fo:table>
                                                                <fo:table-column column-width="80mm"/>
                                                                <fo:table-column column-width="30mm"/>
                                                                <fo:table-column column-width="30mm"/>
                                                                <fo:table-column column-width="30mm"/>
                                                                <fo:table-body>
                                                                    <fo:table-row margin="3mm">
                                                                        <fo:table-cell border-right-style="solid" border-right-width="0.25mm" border-right-color="green" padding-before="3mm" padding-after="3mm">
                                                                            <fo:block>
                                                                                <fo:list-block>
                                                                                    <fo:list-item>
                                                                                        <fo:list-item-label margin-left="2mm">
                                                                                            <fo:block>
                                                                                                <fo:character character="&#x2022;"/>
                                                                                            </fo:block>
                                                                                        </fo:list-item-label>
                                                                                        <fo:list-item-body margin-left="2mm" padding-left="5mm">
                                                                                            <fo:block keep-together.within-page="always" text-align="justify">
                                                                                                Roof panels that turn the sun's energy into electricity
                                                                                            </fo:block>
                                                                                        </fo:list-item-body>
                                                                                    </fo:list-item>                                                        
                                                                                    <fo:list-item>
                                                                                        <fo:list-item-label margin-left="2mm">
                                                                                            <fo:block>
                                                                                                <fo:character character="&#x2022;"/>
                                                                                            </fo:block>
                                                                                        </fo:list-item-label>
                                                                                        <fo:list-item-body margin-left="2mm" padding-left="5mm">
                                                                                            <fo:block keep-together.within-page="always" text-align="justify">
                                                                                                You can use the electricity in your home
                                                                                            </fo:block>
                                                                                        </fo:list-item-body>
                                                                                    </fo:list-item>                                                        
                                                                                    <fo:list-item>
                                                                                        <fo:list-item-label margin-left="2mm">
                                                                                            <fo:block>
                                                                                                <fo:character character="&#x2022;"/>
                                                                                            </fo:block>
                                                                                        </fo:list-item-label>
                                                                                        <fo:list-item-body margin-left="2mm" padding-left="5mm">
                                                                                            <fo:block keep-together.within-page="always" text-align="justify">
                                                                                                Reduces the amount of electricity you need to buy from your supplier
                                                                                            </fo:block>
                                                                                        </fo:list-item-body>
                                                                                    </fo:list-item>                                                        
                                                                                    <fo:list-item>
                                                                                        <fo:list-item-label margin-left="2mm">
                                                                                            <fo:block>
                                                                                                <fo:character character="&#x2022;"/>
                                                                                            </fo:block>
                                                                                        </fo:list-item-label>
                                                                                        <fo:list-item-body margin-left="2mm" padding-left="5mm">
                                                                                            <fo:block keep-together.within-page="always" text-align="justify">
                                                                                                Savings depend on things like which panels you choose and how much sunlight they get. Also depend on how much of the solar electricity you use - I've assumed you'll use half of it
                                                                                            </fo:block>
                                                                                        </fo:list-item-body>
                                                                                    </fo:list-item>                                                        
                                                                                    <fo:list-item>
                                                                                        <fo:list-item-label margin-left="2mm">
                                                                                            <fo:block>
                                                                                                <fo:character character="&#x2022;"/>
                                                                                            </fo:block>
                                                                                        </fo:list-item-label>
                                                                                        <fo:list-item-body margin-left="2mm" padding-left="5mm">
                                                                                            <fo:block keep-together.within-page="always" text-align="justify">
                                                                                                You could get paid for making this electricity, plus extra for what you don't use - see part 3. An average system fitted in April 2016 pays about £X a year
                                                                                            </fo:block>
                                                                                        </fo:list-item-body>
                                                                                    </fo:list-item>                                                        
                                                                                </fo:list-block>
                                                                            </fo:block>
                                                                        </fo:table-cell>
                                                                        <fo:table-cell border-right-style="solid" border-right-width="0.25mm" border-right-color="green" padding-before="3mm" padding-after="3mm">
                                                                            <fo:block text-align="center">
                                                                                £xx
                                                                            </fo:block>
                                                                        </fo:table-cell>
                                                                        <fo:table-cell border-right-style="solid" border-right-width="0.25mm" border-right-color="green" padding-before="3mm" padding-after="3mm">
                                                                            <fo:block text-align="center">
                                                                                £xx to £yy
                                                                            </fo:block>
                                                                        </fo:table-cell>
                                                                        <fo:table-cell padding-before="3mm" padding-after="3mm">
                                                                            <fo:block-container >
                                                                                <fo:block padding-bottom="2mm">
                                                                                    Green Deal loan
                                                                                </fo:block>
                                                                                <fo:block padding-bottom="2mm">
                                                                                    Energy Company Obligation
                                                                                </fo:block>
                                                                            </fo:block-container>
                                                                        </fo:table-cell>
                                                                    </fo:table-row>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </fo:block>
                                                    </fo:block-container>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <!-- -->
                                        </fo:table-body>
                                    </fo:table>
                                    <fo:block-container position="absolute" top="-7mm" left="60mm">
                                        <fo:block>
                                            <fo:external-graphic height="24mm" width="24mm" src="/fop/pv.svg"></fo:external-graphic>
                                        </fo:block>
                                    </fo:block-container>
                                </fo:block-container>
                            </fo:table-cell>
                        </fo:table-row>
                        <!-- PV - End -->
                        <!-- Solid Wall Insulation - Start -->
                        <fo:table-row>
                            <fo:table-cell>
                                <fo:block-container keep-together.within-page="always">
                                    <fo:table>
                                        <fo:table-column column-width="170mm"/>
                                        <fo:table-body>
                                            <!-- -->
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block-container width="82mm">
                                                        <fo:block border-style="solid" border-width="0.5mm" border-color="green" fox:border-radius="3mm" background-color="green">
                                                            <fo:table>
                                                                <fo:table-column column-width="82mm"/>
                                                                <fo:table-body>
                                                                    <fo:table-row margin="3mm">
                                                                        <fo:table-cell>
                                                                            <fo:block padding-before="3mm" padding-after="3mm" color="white" font-weight="bold">
                                                                                Solid wall insulation
                                                                            </fo:block>
                                                                            <fo:block>
                                                                                <fo:marker marker-class-name="table-1-footer">
                                                                                    C
                                                                                </fo:marker>
                                                                            </fo:block>
                                                                        </fo:table-cell>
                                                                    </fo:table-row>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </fo:block>
                                                    </fo:block-container>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <!-- -->
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block-container>
                                                        <fo:block border-style="solid" border-width="0.5mm" border-color="green" fox:border-radius="3mm">
                                                            <fo:table>
                                                                <fo:table-column column-width="80mm"/>
                                                                <fo:table-column column-width="30mm"/>
                                                                <fo:table-column column-width="30mm"/>
                                                                <fo:table-column column-width="30mm"/>
                                                                <fo:table-body>
                                                                    <fo:table-row margin="3mm">
                                                                        <fo:table-cell border-right-style="solid" border-right-width="0.25mm" border-right-color="green" padding-before="3mm" padding-after="3mm">
                                                                            <fo:block>
                                                                                <fo:list-block>
                                                                                    <fo:list-item>
                                                                                        <fo:list-item-label margin-left="2mm">
                                                                                            <fo:block>
                                                                                                <fo:character character="&#x2022;"/>
                                                                                            </fo:block>
                                                                                        </fo:list-item-label>
                                                                                        <fo:list-item-body margin-left="2mm" padding-left="5mm">
                                                                                            <fo:block keep-together.within-page="always" text-align="justify">
                                                                                                Cladding that's fixed on to the outside walls of your building
                                                                                            </fo:block>
                                                                                        </fo:list-item-body>
                                                                                    </fo:list-item>                                                        
                                                                                    <fo:list-item>
                                                                                        <fo:list-item-label margin-left="2mm">
                                                                                            <fo:block>
                                                                                                <fo:character character="&#x2022;"/>
                                                                                            </fo:block>
                                                                                        </fo:list-item-label>
                                                                                        <fo:list-item-body margin-left="2mm" padding-left="5mm">
                                                                                            <fo:block keep-together.within-page="always" text-align="justify">
                                                                                                Helps stop heat escaping through your walls
                                                                                            </fo:block>
                                                                                        </fo:list-item-body>
                                                                                    </fo:list-item>                                                        
                                                                                    <fo:list-item>
                                                                                        <fo:list-item-label margin-left="2mm">
                                                                                            <fo:block>
                                                                                                <fo:character character="&#x2022;"/>
                                                                                            </fo:block>
                                                                                        </fo:list-item-label>
                                                                                        <fo:list-item-body margin-left="2mm" padding-left="5mm">
                                                                                            <fo:block keep-together.within-page="always" text-align="justify">
                                                                                                Helps you use less energy to keep your home cosy
                                                                                            </fo:block>
                                                                                        </fo:list-item-body>
                                                                                    </fo:list-item>                                                        
                                                                                </fo:list-block>
                                                                            </fo:block>
                                                                        </fo:table-cell>
                                                                        <fo:table-cell border-right-style="solid" border-right-width="0.25mm" border-right-color="green" padding-before="3mm" padding-after="3mm">
                                                                            <fo:block text-align="center">
                                                                                £xx
                                                                            </fo:block>
                                                                        </fo:table-cell>
                                                                        <fo:table-cell border-right-style="solid" border-right-width="0.25mm" border-right-color="green" padding-before="3mm" padding-after="3mm">
                                                                            <fo:block text-align="center">
                                                                                £xx to £yy
                                                                            </fo:block>
                                                                        </fo:table-cell>
                                                                        <fo:table-cell padding-before="3mm" padding-after="3mm">
                                                                            <fo:block-container >
                                                                                <fo:block padding-bottom="2mm">
                                                                                    Green Deal loan
                                                                                </fo:block>
                                                                                <fo:block padding-bottom="2mm">
                                                                                    Energy Company Obligation
                                                                                </fo:block>
                                                                            </fo:block-container>
                                                                        </fo:table-cell>
                                                                    </fo:table-row>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </fo:block>
                                                    </fo:block-container>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <!-- -->
                                        </fo:table-body>
                                    </fo:table>
                                    <fo:block-container position="absolute" top="-7mm" left="60mm">
                                        <fo:block>
                                            <fo:external-graphic height="24mm" width="24mm" src="/fop/solid_wall_insulation.svg"></fo:external-graphic>
                                        </fo:block>
                                    </fo:block-container>
                                </fo:block-container>
                            </fo:table-cell>
                        </fo:table-row>
                        <!-- Solid Wall Insulation - End -->
                        
                        <!-- PV - Start -->
                        <fo:table-row>
                            <fo:table-cell>
                                <fo:block-container keep-together.within-page="always">
                                    <fo:table>
                                        <fo:table-column column-width="170mm"/>
                                        <fo:table-body>
                                            <!-- -->
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block-container width="82mm">
                                                        <fo:block border-style="solid" border-width="0.5mm" border-color="green" fox:border-radius="3mm" background-color="green">
                                                            <fo:table>
                                                                <fo:table-column column-width="82mm"/>
                                                                <fo:table-body>
                                                                    <fo:table-row margin="3mm">
                                                                        <fo:table-cell>
                                                                            <fo:block padding-before="3mm" padding-after="3mm" color="white" font-weight="bold" margin-right="14mm">
                                                                                Solar electric panels – known as PV panels
                                                                            </fo:block>
                                                                            <fo:block>
                                                                                <fo:marker marker-class-name="table-1-footer">
                                                                                    D 
                                                                                </fo:marker>
                                                                            </fo:block>
                                                                        </fo:table-cell>
                                                                    </fo:table-row>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </fo:block>
                                                    </fo:block-container>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <!-- -->
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block-container>
                                                        <fo:block border-style="solid" border-width="0.5mm" border-color="green" fox:border-radius="3mm">
                                                            <fo:table>
                                                                <fo:table-column column-width="80mm"/>
                                                                <fo:table-column column-width="30mm"/>
                                                                <fo:table-column column-width="30mm"/>
                                                                <fo:table-column column-width="30mm"/>
                                                                <fo:table-body>
                                                                    <fo:table-row margin="3mm">
                                                                        <fo:table-cell border-right-style="solid" border-right-width="0.25mm" border-right-color="green" padding-before="3mm" padding-after="3mm">
                                                                            <fo:block>
                                                                                <fo:list-block>
                                                                                    <fo:list-item>
                                                                                        <fo:list-item-label margin-left="2mm">
                                                                                            <fo:block>
                                                                                                <fo:character character="&#x2022;"/>
                                                                                            </fo:block>
                                                                                        </fo:list-item-label>
                                                                                        <fo:list-item-body margin-left="2mm" padding-left="5mm">
                                                                                            <fo:block keep-together.within-page="always" text-align="justify">
                                                                                                Roof panels that turn the sun's energy into electricity
                                                                                            </fo:block>
                                                                                        </fo:list-item-body>
                                                                                    </fo:list-item>                                                        
                                                                                    <fo:list-item>
                                                                                        <fo:list-item-label margin-left="2mm">
                                                                                            <fo:block>
                                                                                                <fo:character character="&#x2022;"/>
                                                                                            </fo:block>
                                                                                        </fo:list-item-label>
                                                                                        <fo:list-item-body margin-left="2mm" padding-left="5mm">
                                                                                            <fo:block keep-together.within-page="always" text-align="justify">
                                                                                                You can use the electricity in your home
                                                                                            </fo:block>
                                                                                        </fo:list-item-body>
                                                                                    </fo:list-item>                                                        
                                                                                    <fo:list-item>
                                                                                        <fo:list-item-label margin-left="2mm">
                                                                                            <fo:block>
                                                                                                <fo:character character="&#x2022;"/>
                                                                                            </fo:block>
                                                                                        </fo:list-item-label>
                                                                                        <fo:list-item-body margin-left="2mm" padding-left="5mm">
                                                                                            <fo:block keep-together.within-page="always" text-align="justify">
                                                                                                Reduces the amount of electricity you need to buy from your supplier
                                                                                            </fo:block>
                                                                                        </fo:list-item-body>
                                                                                    </fo:list-item>                                                        
                                                                                    <fo:list-item>
                                                                                        <fo:list-item-label margin-left="2mm">
                                                                                            <fo:block>
                                                                                                <fo:character character="&#x2022;"/>
                                                                                            </fo:block>
                                                                                        </fo:list-item-label>
                                                                                        <fo:list-item-body margin-left="2mm" padding-left="5mm">
                                                                                            <fo:block keep-together.within-page="always" text-align="justify">
                                                                                                Savings depend on things like which panels you choose and how much sunlight they get. Also depend on how much of the solar electricity you use - I've assumed you'll use half of it
                                                                                            </fo:block>
                                                                                        </fo:list-item-body>
                                                                                    </fo:list-item>                                                        
                                                                                    <fo:list-item>
                                                                                        <fo:list-item-label margin-left="2mm">
                                                                                            <fo:block>
                                                                                                <fo:character character="&#x2022;"/>
                                                                                            </fo:block>
                                                                                        </fo:list-item-label>
                                                                                        <fo:list-item-body margin-left="2mm" padding-left="5mm">
                                                                                            <fo:block keep-together.within-page="always" text-align="justify">
                                                                                                You could get paid for making this electricity, plus extra for what you don't use - see part 3. An average system fitted in April 2016 pays about £X a year
                                                                                            </fo:block>
                                                                                        </fo:list-item-body>
                                                                                    </fo:list-item>                                                        
                                                                                </fo:list-block>
                                                                            </fo:block>
                                                                        </fo:table-cell>
                                                                        <fo:table-cell border-right-style="solid" border-right-width="0.25mm" border-right-color="green" padding-before="3mm" padding-after="3mm">
                                                                            <fo:block text-align="center">
                                                                                £xx
                                                                            </fo:block>
                                                                        </fo:table-cell>
                                                                        <fo:table-cell border-right-style="solid" border-right-width="0.25mm" border-right-color="green" padding-before="3mm" padding-after="3mm">
                                                                            <fo:block text-align="center">
                                                                                £xx to £yy
                                                                            </fo:block>
                                                                        </fo:table-cell>
                                                                        <fo:table-cell padding-before="3mm" padding-after="3mm">
                                                                            <fo:block-container >
                                                                                <fo:block padding-bottom="2mm">
                                                                                    Green Deal loan
                                                                                </fo:block>
                                                                                <fo:block padding-bottom="2mm">
                                                                                    Energy Company Obligation
                                                                                </fo:block>
                                                                            </fo:block-container>
                                                                        </fo:table-cell>
                                                                    </fo:table-row>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </fo:block>
                                                    </fo:block-container>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <!-- -->
                                        </fo:table-body>
                                    </fo:table>
                                    <fo:block-container position="absolute" top="-7mm" left="60mm">
                                        <fo:block>
                                            <fo:external-graphic height="24mm" width="24mm" src="/fop/pv.svg"></fo:external-graphic>
                                        </fo:block>
                                    </fo:block-container>
                                </fo:block-container>
                            </fo:table-cell>
                        </fo:table-row>
                        <!-- PV - End -->

                    </fo:table-body>
                </fo:table>
            </fo:block-container>
        </fo:flow>
    </fo:page-sequence>
    
</fo:root>
