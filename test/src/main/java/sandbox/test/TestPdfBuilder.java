package sandbox.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import lombok.Data;

import org.apache.commons.configuration.MapConfiguration;
import org.apache.commons.io.FileUtils;

import sandbox.pdfbuilder.PdfBuilder;
import sandbox.pdfbuilder.fo.freemarker.FreemarkerFoProvider;
import sandbox.pdfbuilder.util.PdfEncryption;
import sandbox.test.data.ComparisonData;
import sandbox.test.data.Dosh;
import sandbox.test.data.GdarData;
import sandbox.test.data.ImprovementData;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.utility.StringUtil;

public class TestPdfBuilder
{
    public static void main(String [] args) throws Exception
    {
        new TestPdfBuilder().go();
    }
    
    public void go() throws Exception
    {     
        System.out.println(getGdarData());
        
        //dictionary().entrySet().forEach(e -> {System.out.println(e.getKey() + "=" + e.getValue());});
        
        PdfBuilder.foProvider(FreemarkerFoProvider.Builder
                                                  .templateLoader(new ClassTemplateLoader(this.getClass(), "/gdar-pdf")).build()
                                                  .template("gdar_002.ftl")
                                                  .modelHelper("gdarUtils", new GdarUtils())
                                                  .modelParam("dictionary", dictionary())
                                                  .modelParam("data", getGdarData()))
                                                  .pdfEncryption(PdfEncryption.with().allowPrint(true))
                                                  .build(FileUtils.openOutputStream(outputFile()));
        
    }

    // Mimic loading properties from DB and interpolating values using Apache Configuration
    private Map<String, String> dictionary() throws Exception
    {
        Properties prop = new Properties();
        prop.load(FileUtils.openInputStream(new File("src/main/resources/dictionary.properties")));
        Map<String, String> propertiesMap = prop.entrySet().stream().collect(Collectors.toMap(entry -> "" + entry.getKey(), entry ->  "" + entry.getValue()));

        
        MapConfiguration mapConfiguration = new MapConfiguration(propertiesMap);
        mapConfiguration.setDelimiterParsingDisabled(true);
        Map<String, String> map = new HashMap<>();
        mapConfiguration.getKeys().forEachRemaining(k -> {map.put(k, mapConfiguration.getString(k));});
        
        
        return map;
    }
    
    private Properties dictionary01() throws Exception
    {
        Properties prop = new Properties();
        prop.load(FileUtils.openInputStream(new File("src/main/resources/dictionary.properties")));

        //MapConfiguration mc = new MapConfiguration(prop);
        //return mc.getMap();
        
        return prop;
    }
    
    private GdarData getGdarData()
    {
        GdarData data = 
               GdarData.with()
                       .setVersion(GdarData.V2_0)
                       .setOaRrn("1111-2222-3333-4444-5555-6666")
                       .setInspectionDate("30 May 2016")
                       .setLodgementDate("31 May 2016")
                       .setReportDate("1 June 2016")
                       .setUprn("1234512345")
                       .addressLine("123 Green Street", "A Neck of the Woods", "Boilerton", "DE1 9CC")
                       .setAdvisorName("Alan Clifford")
                       .setAdvisorId("SMOU109999")
                       .setAdvisorPhone("01234 123456")
                       .setAdvisorEmail("a.clifford@gdemail.co.uk")
                       .setRelatedPartyDisclosureKey("No related party")
                       .setSavingsElectricGbp("1111").setSavingsElectricKwh("1112")
                       .setSavingsGasGbp("2111").setSavingsGasKwh("2112")
                       .setSavingsOtherGbp("3111").setSavingsOtherKwh("3112")
                       .setSpaceHeatingKwh("4111")
                       .setWaterHeatingKwh("5111")
                       .setApplianceHeatingKwh("6111")
                       .setFormattedSchemeName("Seamouse Certification Ltd")
                       .setFormattedSchemeWebsite("www.seamouse-certification.co.uk")
                       .setAssessorOrgName(StringUtil.XMLEnc("Clifford & Co Assessors Ltd"))
                       .setAssessorOrgId("BNSA12345")
                       .setSoftwareNameAndVersion("Green Deal Calc Software v1.0")
                       .setUiNameAndVersion("Blarg UI 2.1.1")
                       .setComparisonData(ComparisonData.with()
                                                        .setStandardOccupants("3")
                                                        .setActualOccupants("4")
                                                        .setStandardHeatingHours("11")
                                                        .setActualHeatingHours("9.5")
                                                        .setStandardThermostat("21")
                                                        .setActualThermostat("21")
                                                        .setStandardUnheatedRooms("0")
                                                        .setActualUnheatedRooms("0")
                                                        .setActualCost("1200")
                                                        .setStandardCost("1400")
                                                        .setScaleGrads(new String[] {"200", "300", "600", "800", "1000", "1200", "1400", "1600", "1800", "2000"}));

        data.setImprovements(FluentList.<ImprovementData>with()
                                        /*.data(ImprovementData.parent(data).setIdx(25).setType("Q").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(124)).setIndicativeCost("£100 to £1,000").setFootnote(12).setBadge("GDHIF", "ECO").setReplacesBothHeatingSystems(false).setLifetime(25).setUValue("6").setArgs("external", "6", null))*/
                                        .data(ImprovementData.parent(data).setIdx(1).setType("A").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(100)).setFootnote(1).setBadge("ECO").setReplacesBothHeatingSystems(false).setLifetime(25).setUValue("1").setArgs("", "1"))
                                        .data(ImprovementData.parent(data).setIdx(2).setType("A2").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(101)).setFootnote(2).setBadge("GDHIF").setReplacesBothHeatingSystems(false).setLifetime(25).setUValue("2").setArgs("external and internal", "2"))
                                        .data(ImprovementData.parent(data).setIdx(3).setType("A3").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(102)).setFootnote(3).setBadge("GDHIF", "ECO").setReplacesBothHeatingSystems(false).setLifetime(25).setArgs("1", "3", "4", "2"))
                                        .data(ImprovementData.parent(data).setIdx(4).setType("B").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(103)).setFootnote(4, 5, 6, 7, 8).setBadge("GDHIF", "ECO").setReplacesBothHeatingSystems(false).setLifetime(25))
                                        /*
                                        .data(ImprovementData.parent(data).setIdx(5).setType("B4").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(104)).setFootnote().setBadge("ECO").setReplacesBothHeatingSystems(false).setLifetime(25))
                                        .data(ImprovementData.parent(data).setIdx(6).setType("C").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(105)).setFootnote(9).setBadge("ECO").setReplacesBothHeatingSystems(false).setLifetime(25))
                                        .data(ImprovementData.parent(data).setIdx(7).setType("D").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(106)).setFootnote().setBadge("ECO").setReplacesBothHeatingSystems(false).setLifetime(25))
                                        .data(ImprovementData.parent(data).setIdx(8).setType("E2").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(107)).setFootnote().setBadge().setReplacesBothHeatingSystems(false).setLifetime(25).setArgs("20", "10"))
                                        .data(ImprovementData.parent(data).setIdx(9).setType("EP").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(108)).setFootnote().setBadge().setReplacesBothHeatingSystems(false).setLifetime(25))
                                        .data(ImprovementData.parent(data).setIdx(10).setType("F").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(109)).setFootnote(9).setBadge().setReplacesBothHeatingSystems(false).setLifetime(25))
                                        .data(ImprovementData.parent(data).setIdx(11).setType("G").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(110)).setFootnote().setBadge().setReplacesBothHeatingSystems(false).setLifetime(25).setArgs("programmer", "room thermostat and TRVs"))
                                        .data(ImprovementData.parent(data).setIdx(12).setType("H").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(111)).setFootnote().setBadge().setReplacesBothHeatingSystems(false).setLifetime(25))
                                        .data(ImprovementData.parent(data).setIdx(13).setType("I").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(112)).setFootnote().setBadge("GDHIF").setReplacesBothHeatingSystems(false).setLifetime(25).setArgs("bulk wood pellets", "&gt;45SUP_oC, flowTemperature=1"))
                                        .data(ImprovementData.parent(data).setIdx(14).setType("I+T2").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(113)).setFootnote().setBadge().setReplacesBothHeatingSystems(false).setLifetime(25).setArgs("bulk wood pellets", "unknown"))
                                        .data(ImprovementData.parent(data).setIdx(15).setType("J").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(114)).setFootnote().setBadge("RHI").setReplacesBothHeatingSystems(false).setLifetime(25))
                                        .data(ImprovementData.parent(data).setIdx(16).setType("K").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(115)).setFootnote().setBadge("RHI").setReplacesBothHeatingSystems(false).setLifetime(25))
                                        .data(ImprovementData.parent(data).setIdx(17).setType("L").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(116)).setFootnote().setBadge("GDHIF").setReplacesBothHeatingSystems(false).setLifetime(25))
                                        .data(ImprovementData.parent(data).setIdx(18).setType("L2").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(117)).setFootnote().setBadge().setReplacesBothHeatingSystems(false).setLifetime(25))
                                        .data(ImprovementData.parent(data).setIdx(19).setType("M").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(118)).setFootnote().setBadge("GDHIF").setReplacesBothHeatingSystems(false).setLifetime(25))
                                        .data(ImprovementData.parent(data).setIdx(20).setType("N").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(119)).setFootnote().setBadge("RHI").setReplacesBothHeatingSystems(false).setLifetime(25).setArgs("flat panel, panel area 40mSUP_2", "efficiency 0.5", "heat loss coefficients 75 W/mSUP_2K and 65 W/mSUP_2KSUP_2, orientation N, horizontal"))
                                        .data(ImprovementData.parent(data).setIdx(21).setType("O").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(120)).setFootnote(10).setBadge("GDHIF", "ECO").setReplacesBothHeatingSystems(false).setLifetime(25).setUValue("3").setArgs("30", "3", "8"))
                                        .data(ImprovementData.parent(data).setIdx(22).setType("O2").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(121)).setFootnote(11).setBadge("GDHIF", "ECO").setReplacesBothHeatingSystems(false).setLifetime(25).setUValue("4").setArgs("40", "4", "69"))
                                        .data(ImprovementData.parent(data).setIdx(23).setType("O3").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(122)).setFootnote().setBadge("ECO").setReplacesBothHeatingSystems(false).setLifetime(25))
                                        .data(ImprovementData.parent(data).setIdx(24).setType("P").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(123)).setFootnote(10).setBadge("GDHIF", "ECO").setReplacesBothHeatingSystems(false).setLifetime(25).setUValue("5").setArgs("30", "5", "13"))
                                        .data(ImprovementData.parent(data).setIdx(25).setType("Q").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(124)).setFootnote(12).setBadge("GDHIF", "ECO").setReplacesBothHeatingSystems(false).setLifetime(25).setUValue("6").setArgs("external", "6", null))
                                        .data(ImprovementData.parent(data).setIdx(26).setType("Q1").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(125)).setFootnote(13).setBadge("GDHIF", "ECO").setReplacesBothHeatingSystems(false).setLifetime(25).setUValue("7").setArgs("internal", "7", null, ""))
                                        .data(ImprovementData.parent(data).setIdx(27).setType("Q2").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(126)).setFootnote(14).setBadge("GDHIF", "ECO").setReplacesBothHeatingSystems(false).setLifetime(25).setUValue("8").setArgs("external", "8", null))
                                        .data(ImprovementData.parent(data).setIdx(28).setType("R").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(127)).setFootnote().setBadge().setReplacesBothHeatingSystems(false).setLifetime(25).setArgs("unknown"))
                                        .data(ImprovementData.parent(data).setIdx(29).setType("S").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(128)).setFootnote().setBadge("GDHIF").setReplacesBothHeatingSystems(false).setLifetime(25).setArgs("between 36 and 45SUP_oC", "flowTemperature=3"))
                                        .data(ImprovementData.parent(data).setIdx(30).setType("S+T2").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(129)).setFootnote().setBadge().setReplacesBothHeatingSystems(false).setLifetime(25).setArgs("unknown"))
                                        .data(ImprovementData.parent(data).setIdx(31).setType("T").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(130)).setFootnote(15).setBadge("GDHIF").setReplacesBothHeatingSystems(false).setLifetime(25).setArgs("between 36 and 45SUP_oC", "flowTemperature=3"))
                                        .data(ImprovementData.parent(data).setIdx(32).setType("T+T2").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(131)).setFootnote(15).setBadge().setReplacesBothHeatingSystems(false).setLifetime(25).setArgs("unknown"))
                                        .data(ImprovementData.parent(data).setIdx(33).setType("U").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(132)).setFootnote(16).setBadge("FIT").setReplacesBothHeatingSystems(false).setLifetime(25).setArgs("1 kWp, orientation N, horizontal"))
                                        .data(ImprovementData.parent(data).setIdx(34).setType("V2").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(133)).setFootnote(16).setBadge("FIT").setReplacesBothHeatingSystems(false).setLifetime(25).setArgs("100", "50", "80"))
                                        .data(ImprovementData.parent(data).setIdx(36).setType("W1").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(135)).setFootnote(17).setBadge("GDHIF", "ECO").setReplacesBothHeatingSystems(false).setLifetime(25).setUValue("10").setArgs("10"))
                                        .data(ImprovementData.parent(data).setIdx(37).setType("W2").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(136)).setFootnote(17).setBadge("GDHIF", "ECO").setReplacesBothHeatingSystems(false).setLifetime(25).setUValue("11").setArgs("11"))
                                        .data(ImprovementData.parent(data).setIdx(38).setType("X").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(137)).setFootnote().setBadge("GDHIF", "ECO").setReplacesBothHeatingSystems(false).setLifetime(25))
                                        .data(ImprovementData.parent(data).setIdx(39).setType("Y").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(138)).setFootnote().setBadge("GDHIF").setReplacesBothHeatingSystems(false).setLifetime(25))
                                        .data(ImprovementData.parent(data).setIdx(40).setType("Z1").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(139)).setFootnote().setBadge("RHI").setReplacesBothHeatingSystems(false).setLifetime(25).setArgs("unknown"))
                                        .data(ImprovementData.parent(data).setIdx(41).setType("Z3").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(140)).setFootnote(15, 16).setBadge("FIT").setReplacesBothHeatingSystems(false).setLifetime(25))
                                        .data(ImprovementData.parent(data).setIdx(42).setType("Z4").setEstimatedSaving(Dosh.value(1000)).setTypicalSaving(Dosh.value(141)).setFootnote().setBadge("RHI").setReplacesBothHeatingSystems(false).setLifetime(25).setArgs("unknown"))
                                        */
                );
        
        return data;
    
    }
    
    
    private File outputFile()
    {
        File outputFile = new File("target/generated-pdf/test_" + System.currentTimeMillis() + ".pdf");
        System.out.println(outputFile.getAbsolutePath());
        return outputFile;
    }
    
    @Data(staticConstructor = "with")
    private static class FluentList<T> extends ArrayList<T>
    {
        public FluentList<T> data(T data)
        {
            super.add(data);
            return this;
        }
    }
    
    @Data(staticConstructor = "with")
    private static class FluentSet<T> extends HashSet<T>
    {
        public FluentSet<T> data(T data)
        {
            super.add(data);
            return this;
        }
    }
    
}
