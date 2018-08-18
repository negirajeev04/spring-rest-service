package org.rajnegi.spring.rest.webservices.springrestservice.resources;

import java.util.Arrays;
import java.util.List;

import org.rajnegi.spring.rest.webservices.springrestservice.bean.DynamicFilterBean;
import org.rajnegi.spring.rest.webservices.springrestservice.bean.FilterBean;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringResource {

	@GetMapping(value="/filter")
	public FilterBean filterBean() {
		return new FilterBean("value1","value2","value3");
	}
	
	@GetMapping(value="filter-list")
	public List<FilterBean> filterList(){
		return Arrays.asList(new FilterBean("value1","value2","value3"),new FilterBean("value12","value22","value32"));
	}
	
	//field1, field2
	@GetMapping(value="/dynamicfilter")
	public MappingJacksonValue dynaFilter() {
		
		DynamicFilterBean bean = new DynamicFilterBean("value1","value2","value3");
		
		MappingJacksonValue mapping = new MappingJacksonValue(bean);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field3");
		FilterProvider filters = new SimpleFilterProvider().addFilter("DynamicFilter", filter);
		
		mapping.setFilters(filters);
		
		return mapping;
	}
	
}
