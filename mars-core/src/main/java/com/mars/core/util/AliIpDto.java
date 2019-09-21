package com.mars.core.util;

public class AliIpDto {
	
	
	private int code;
	
	private AliIpDtoData data ; 
	
	
	
	public int getCode(){
		return code;
	}



	public void setCode(int code){
		this.code = code;
	}



	public AliIpDtoData getData(){
		return data;
	}



	public void setData(AliIpDtoData data){
		this.data = data;
	}



	public class  AliIpDtoData{
		private String country;
		private String area;
		private String region;
		private String city;
		
		private String county;
		
		private String isp;

		public String getCountry(){
			return country;
		}

		public void setCountry(String country){
			this.country = country;
		}

		public String getArea(){
			return area;
		}

		public void setArea(String area){
			this.area = area;
		}

		public String getRegion(){
			return region;
		}

		public void setRegion(String region){
			this.region = region;
		}

		public String getCity(){
			return city;
		}

		public void setCity(String city){
			this.city = city;
		}

		public String getCounty(){
			return county;
		}

		public void setCounty(String county){
			this.county = county;
		}

		public String getIsp(){
			return isp;
		}

		public void setIsp(String isp){
			this.isp = isp;
		}
		
		
		
	}

}



//{"code":0,"data":{"country":"中国","country_id":"CN","area":"华东","area_id":"300000","region":"浙江省","region_id":"330000","city":"杭州市","city_id":"330100","county":"","county_id":"-1","isp":"阿里云","isp_id":"1000323","ip":"121.43.230.33"}}
