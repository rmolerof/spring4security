package com.jasperreport;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import corp.Application;
import corp.domain.DNIDao;
import corp.domain.DNIsRepository;
import corp.services.Utils;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRException;

@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class GenerateReportServiceTest {

	@Autowired
    private DNIsRepository dnisRepository;
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private Utils utils;
	
	private String basePath;
	
//	@Test
	public void generateDynamicReportTest() throws IOException{
		
		JasperReportBuilder report = DynamicReports.report();//a new report
		report
		  .columns(
		  	Columns.column("id", "dni", DataTypes.stringType())
		  		.setHorizontalAlignment(HorizontalAlignment.LEFT),
		  	Columns.column("firstName", "nombre", DataTypes.stringType()),
		  	Columns.column("firstName", "paterno", DataTypes.stringType())
		  		.setHorizontalAlignment(HorizontalAlignment.LEFT)
		  	)
		  .title(//title of the report
		  	Components.text("Customer Report")
		  		.setHorizontalAlignment(HorizontalAlignment.CENTER))
		  .pageFooter(Components.pageXofY())//show page number on the page footer
		  .setDataSource(getCollections());
		
		try {
			//report.show();
			utils.deletePath(getBasePath() + "/jasperReports");
			report.toPdf(new FileOutputStream(getBasePath() + "/jasperReports/dynamic-pdf.pdf"));//export the report to a pdf file
//			report.toXlsx(new FileOutputStream(getBasePath() + "/jasperReports/dynamic-xsl.xlsx"));
//			report.toDocx(new FileOutputStream(getBasePath() + "/jasperReports/dynamic-docs.docx"));
		} catch (DRException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private Collection<DNIDao> getCollections(){

        Collection<DNIDao> list = new ArrayList<>();
        for (DNIDao customer : dnisRepository.findAll()) {
        	list.add(customer);	        
        }
        return list;
	}
	
	@Test
	public void generateReport() throws JRException {
		
		String invoiceNbr = "F001-00000030";
		utils.generateReport(invoiceNbr);
		
		File f = new File(getBasePath() + "/jasperReports/laJoyaInvoice_" + invoiceNbr + ".pdf");
		assertTrue(f.exists() && !f.isDirectory()); 
		
	}
	
	public String getBasePath() {
		if (null == this.basePath) {
			try {
				this.basePath = resourceLoader.getResource("classpath:/static/").getFile().getPath();
				
				return this.basePath;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	
}