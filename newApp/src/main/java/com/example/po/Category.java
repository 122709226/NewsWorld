package com.example.po;

public class Category {

	public int getT_id() {
		return t_id;
	}
	public void setT_id(int t_id) {
		this.t_id = t_id;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	private int t_id;
     private String kind;
	public Category(int t_id, String kind) {
		super();
		this.t_id = t_id;
		this.kind = kind;
	}
     
    @Override
	public String toString() {
		// TODO Auto-generated method stub
		return kind;
	}
     
}
