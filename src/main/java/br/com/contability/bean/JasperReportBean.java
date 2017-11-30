package br.com.contability.bean;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsViewResolver;

@Configuration
public class JasperReportBean {
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public JasperReportsViewResolver getJasperReportsViewResolver() {
	  JasperReportsViewResolver resolver = new JasperReportsViewResolver();
	  resolver.setPrefix("classpath:/jasperreports/");
	  resolver.setJdbcDataSource(dataSource);
	  resolver.setSuffix(".jasper");
	  resolver.setReportDataKey("datasource");
	  resolver.setViewNames("rpt_*");
	  resolver.setViewClass(JasperReportsMultiFormatView.class);
	  resolver.setOrder(0);
	  return resolver;
	}  

}
