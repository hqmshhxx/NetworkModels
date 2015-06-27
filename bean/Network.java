package bean;

import java.util.Map;

public class Network {

	private boolean isDirected = false;
	private int nodeNumber;
	private int edgeNumber;
	private Map<Integer, Node> nodeMap;

	public boolean isDirected() {
		return isDirected;
	}

	public void setDirected(boolean isDirected) {
		this.isDirected = isDirected;
	}

	public int getNodeNumber() {
		return nodeNumber;
	}

	public void setNodeNumber(Map<Integer, Node> nodeMap) {
		this.nodeNumber = nodeMap.size();
	}

	public int getEdgeNumber() {
		return edgeNumber;
	}

	public void setEdgeNumber(Map<Integer, Node> nodeMap) {

		int size = 0;
		for (Integer key : nodeMap.keySet()) {
			size += nodeMap.get(key).getOutEdges().size();
			size += nodeMap.get(key).getInEdges().size();
		}
		if (isDirected) {
			this.edgeNumber = size;
		} else {
			this.edgeNumber = size / 2;
		}
	}

	public Map<Integer, Node> getNodeMap() {
		return nodeMap;
	}

	public void setNodeMap(Map<Integer, Node> nodeMap) {
		this.nodeMap = nodeMap;
	}

}
