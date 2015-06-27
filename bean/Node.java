package bean;


import java.util.LinkedList;

public class Node {
 
	private double flg,begin,stop;
	private int id;
	private String name;
	private LinkedList<Integer>outEdges;
	private LinkedList<Integer>inEdges;
	Node(String name,int id){
		this.name=name;
		this.id=id;
		this.outEdges=new LinkedList<Integer>();
		this.inEdges=new LinkedList<Integer>();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public double getFlg() {
		return flg;
	}
	public void setFlg(double flg) {
		this.flg = flg;
	}
	public double getBegin() {
		return begin;
	}
	public void setBegin(double begin) {
		this.begin = begin;
	}
	public double getStop() {
		return stop;
	}
	public void setStop(double stop) {
		this.stop = stop;
	}
	public LinkedList<Integer> getOutEdges() {
		return outEdges;
	}
	public void setOutEdges(LinkedList<Integer> outEdges) {
		this.outEdges = outEdges;
	}
	public LinkedList<Integer> getInEdges() {
		return inEdges;
	}
	public void setInEdges(LinkedList<Integer> inEdges) {
		this.inEdges = inEdges;
	}
	boolean addOutEdge(Integer id){
		return outEdges.add(id);
	}
	boolean deleteOutEdge(Integer id){
		return outEdges.remove(id);
	}
	boolean addInEdge(Integer id){
		return inEdges.add(id);
	}
	boolean deleteInEdge(Integer id){
		return inEdges.remove(id);
	}
}
