package com.acuteksolutions.uhotel.mvp.model.movies;

public class Movies{
	private String result;
	private int code;
	private String type;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	@Override
 	public String toString(){
		return 
			"Movies{" + 
			"result = '" + result + '\'' + 
			",code = '" + code + '\'' + 
			",type = '" + type + '\'' + 
			"}";
		}
}
