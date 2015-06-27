package bean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class GenerateNetwork {

	String nodeNumber;
	String averageDegree;
	String probability;
	String fileName;
	String newAverageDegree;
	String clusteringCoefficient;
	String averagePath;
	String newNodeDegree;
	public String getNodeNumber() {
		return nodeNumber;
	}
	public void setNodeNumber(String nodeNumber) {
		this.nodeNumber = nodeNumber;
	}
	public String getAverageDegree() {
		return averageDegree;
	}
	public void setAverageDegree(String averageDegree) {
		this.averageDegree = averageDegree;
	}
	public String getProbability() {
		return probability;
	}
	public void setProbability(String probability) {
		this.probability = probability;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getNewAverageDegree() {
		return newAverageDegree;
	}
	public void setNewAverageDegree(String newAverageDegree) {
		this.newAverageDegree = newAverageDegree;
	}
	public String getClusteringCoefficient() {
		return clusteringCoefficient;
	}
	public void setClusteringCoefficient(String clusteringCoefficient) {
		this.clusteringCoefficient = clusteringCoefficient;
	}
	public String getAveragePath() {
		return averagePath;
	}
	public void setAveragePath(String averagePath) {
		this.averagePath = averagePath;
	}
	
	public String getNewNodeDegree() {
		return newNodeDegree;
	}
	public void setNewNodeDegree(String newNodeDegree) {
		this.newNodeDegree = newNodeDegree;
	}
	public void outputNetwork(Network network, String name) {
		Map<Integer, Node> nodeMap = network.getNodeMap();
		try {
			String url=URLDecoder.decode(this.getClass().getResource("/").toString(), "UTF-8");
			String path=url.substring(6, url.length()-16);
			System.out.println("path is "+path);
			File file = new File(path+ name + ".json");

			FileWriter fw = new FileWriter(file,false);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("{\"nodes\":[");
			bw.newLine();
			ArrayList<Integer> nodeMapKey = new ArrayList<Integer>(nodeMap.keySet());
			
			Collections.sort(nodeMapKey);
			for (int i = 0; i < nodeMapKey.size(); i++) {
				bw.write("{\"name\":\"" + nodeMap.get(nodeMapKey.get(i)).getName() + "\",\"group\":"
						+ nodeMap.get(nodeMapKey.get(i)).getOutEdges().size()
						+ "}");
				if (i < nodeMapKey.size() - 1) {
					bw.write(",");
				}
				bw.newLine();
			}
			bw.write("],");
			bw.newLine();

			bw.write("\"links\":[");
			bw.newLine();
			LinkedList<Integer> targets;
			for (int i = 0; i < nodeMapKey.size(); i++) {
				targets = nodeMap.get(nodeMapKey.get(i)).getOutEdges();
				for (int j = 0; j < targets.size(); j++) {
					bw.write("{\"source\":" + nodeMapKey.get(i).intValue()
							+ ",\"target\":" + targets.get(j).intValue() + ",\"value\":1}");
					if (i < nodeMapKey.size() - 1) {
						bw.write(",");
					}
					else{
						if(j<targets.size()-1){
							bw.write(",");
						}
					}
					bw.newLine();
				}			
			}
			bw.write("]");
			bw.newLine();
			bw.write("}");
			
			bw.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

	public float averageDegree(Network network) {

		Map<Integer, Node> nodeMap = network.getNodeMap();
		Iterator<Integer> iterator = nodeMap.keySet().iterator();
		int sum = 0;
		float average = 0;
		while (iterator.hasNext()) {
			if(network.isDirected()){
			sum += nodeMap.get(iterator.next()).getOutEdges().size();
			sum += nodeMap.get(iterator.next()).getInEdges().size();
			}else{
				sum += nodeMap.get(iterator.next()).getOutEdges().size();
			}
		}
		average = sum * 1.0f / nodeMap.size();
		return average;
	}
	public void outDegree(Network network){
		Map<Integer, Node> nodeMap = network.getNodeMap();
		Iterator<Integer> iterator = nodeMap.keySet().iterator();
		Map<Integer, Integer> degreeMap = new HashMap<Integer, Integer>();
		int degree = 0;
		int degreeNum = 0;
		while (iterator.hasNext()) {
			degree = nodeMap.get(iterator.next()).getOutEdges().size();
			if (!degreeMap.containsKey(Integer.valueOf(degree))) {
				degreeMap.put(Integer.valueOf(degree), Integer.valueOf(1));
			} else {
				degreeNum = degreeMap.get(Integer.valueOf(degree)).intValue();
				degreeMap.put(Integer.valueOf(degree),Integer.valueOf(degreeNum + 1));
			}
		}	
		ArrayList<Integer> degreeMapKey = new ArrayList<Integer>(degreeMap.keySet());
		Collections.sort(degreeMapKey);
		
		//Least square method y=ax+b
		double a=0;
		double b=0;
		int sum_xy=0;
		int sumx=0;
		int sumy=0;
		int sum_xsquare=0;
		
		int x=0;
		int y=0;
		int n=degreeMapKey.size();
		for(int i=0;i<n;i++){
			x=degreeMapKey.get(i).intValue();
			y=degreeMap.get(degreeMapKey.get(i)).intValue();
			sumx+=x;
			sumy+=y;
			sum_xsquare+=x*x;
			sum_xy+=x*y;
			
		}
		
		int sumx_square=sumx*sumx;
		
		a=(n*sum_xy-sumx*sumy)*1.0/(n*sum_xsquare-sumx_square);
		b=(sum_xsquare*sumy-sumx*sum_xy)*1.0/(n*sum_xsquare-sumx_square);
		System.out.println("a="+a+"\t b="+b);
/*		
		double x1=-b/(4*a);
		double y1=3*b/4;
		double x2=-3*b/(4*a);
		double y2=b/4;
*/		
		
		
		try {
			String url=URLDecoder.decode(this.getClass().getResource("/").toString(),"UTF-8");
			String path=url.substring(6, url.length()-16);
			System.out.println("path is "+path);
			File file = new File(path+ "outDegree.json");

			FileWriter fw = new FileWriter(file,false);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("{\"dataset\":[");
			bw.newLine();
			
			for (int i = 0; i < degreeMapKey.size(); i++) {
				degree = degreeMapKey.get(i).intValue();
				int val=degreeMap.get(degreeMapKey.get(i).intValue());
				
				bw.write("[" + degree + ","+val+ "]");
				if (i < degreeMapKey.size() - 1) {
					bw.write(",");
				}
				bw.newLine();
			}
			bw.write("],");
			bw.newLine();
			bw.write("\"twonodes\":[");
			bw.newLine();
/*			
			bw.write("[" + x1 + ","+y1+ "],");
			bw.write("[" + x2 + ","+y2+ "]");
*/
			bw.write("[" + 0 + ","+b+ "],");
			bw.write("[" + -b/a + ","+0+ "]");
			bw.newLine();
			bw.write("]}");			
			
			bw.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void degreeDistribution(Network network) {
		Map<Integer, Node> nodeMap = network.getNodeMap();
		Iterator<Integer> iterator = nodeMap.keySet().iterator();
		Map<Integer, Integer> degreeMap = new HashMap<Integer, Integer>();
		int degree = 0;
		int degreeNum = 0;
		while (iterator.hasNext()) {
			degree = nodeMap.get(iterator.next()).getOutEdges().size();
			if (!degreeMap.containsKey(Integer.valueOf(degree))) {
				degreeMap.put(Integer.valueOf(degree), Integer.valueOf(1));
			} else {
				degreeNum = degreeMap.get(Integer.valueOf(degree)).intValue();
				degreeMap.put(Integer.valueOf(degree),Integer.valueOf(degreeNum + 1));
			}
		}
		
//		System.out.println("degreeMap.size() is " + degreeMap.size());
		
		ArrayList<Integer> degreeMapKey = new ArrayList<Integer>(degreeMap.keySet());
		Collections.sort(degreeMapKey);
		
		
		try {
			String url=URLDecoder.decode(this.getClass().getResource("/").toString(),"UTF-8");
			String path=url.substring(6, url.length()-16);
			System.out.println("path is "+path);
			File file = new File(path+ "1050.json");

			FileWriter fw = new FileWriter(file,false);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("{\"dataset\":[");
			bw.newLine();
			
			for (int i = 0; i < degreeMapKey.size(); i++) {
				degree = degreeMapKey.get(i).intValue();
				double val=degreeMap.get(degreeMapKey.get(i).intValue())/ (network.getNodeNumber() * 1.0);
				BigDecimal   bd  =   new  BigDecimal(val);  
				bw.write("[" + degree + ","+
						 bd.setScale(2,  BigDecimal.ROUND_HALF_UP).floatValue() 
						+ "]");
				if (i < degreeMapKey.size() - 1) {
					bw.write(",");
				}
				bw.newLine();
			}
			bw.newLine();
			bw.write("]}");			
			
			bw.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
/*		
		for (int i = 0; i < degreeMapKey.size(); i++) {
			degree = degreeMapKey.get(i).intValue();
			System.out.println("p(k=" + degree + ") is "
					+ degreeMap.get(degreeMapKey.get(i)).intValue()/ (network.getNodeNumber() * 1.0));
		}
*/		
	}
	public float clusteringCoefficient(Network network) {
		
	       
		Map<Integer, Node> nodeMap = network.getNodeMap();
		Iterator<Integer> iterator = nodeMap.keySet().iterator();
		Node node;
		LinkedList<Integer> neighbors;
		float sum = 0;
		int count = 0;
		while (iterator.hasNext()) {// 计算每一个节点的聚类系数
			node = nodeMap.get(iterator.next());// 获得当前结点
			neighbors = node.getOutEdges();// 获得当前结点的邻居结点id
			for (int i = 0; i < neighbors.size(); i++) {
				Integer s = neighbors.get(i);
				for (int j = i + 1; j < neighbors.size(); j++) {
					if (nodeMap.get(s).getOutEdges().contains(neighbors.get(j)))
						count++;
				}
			}
			if (count != 0) {
				sum += count * 2.0/ (neighbors.size() * (neighbors.size() - 1));
			}
			count = 0;
		}
		return sum / nodeMap.size();
	}
	public float averageShortestPath(Network network) {
		float averagePath = 0;
		int sumPath = 0;
		int shortest=0;
		int nodeNumber = network.getNodeNumber();
	
		for (int i = 0; i < nodeNumber; i++) {	
//			System.out.print("node "+i);
			shortest= shortestPath(i, nodeNumber, network);
//			System.out.println(" sssp is "+shortest);
			sumPath +=shortest;
		}
		
		averagePath = sumPath * 1.0f / (nodeNumber * (nodeNumber - 1));
		System.out.println("averagePath is " + averagePath);
		return averagePath;
	}
	public int shortestPath(int startNumber, int disLength, Network network) {
		int pathNum = 0;
		LinkedList<Integer> dist = new LinkedList<Integer>();
		LinkedList<Boolean> s = new LinkedList<Boolean>();
		Map<Integer, Node> nodeMap = network.getNodeMap();
		
		LinkedList<Integer> outNodes = nodeMap.get(Integer.valueOf(startNumber)).getOutEdges();
		for (int i = 0; i < disLength; i++) {
			if (i == startNumber) {
				dist.add(Integer.valueOf(0));
			} else if (outNodes.contains(Integer.valueOf(i))) {
				dist.add(Integer.valueOf(1));
			} else {
				dist.add(Integer.valueOf(Integer.MAX_VALUE));
			}
			s.add(false);
		}
		s.set(startNumber, true);
		LinkedList<Integer> out;
		for (int i = 0; i < disLength; i++) {
			int temp = Integer.MAX_VALUE;
			int u = startNumber;
			for (int j = 0; j < disLength; j++) {
				if ((!s.get(j)) && (dist.get(j).intValue() < temp)) {
					u = j;
					temp = dist.get(j).intValue();
				}
			}
			s.set(u, true);
			out = nodeMap.get(Integer.valueOf(u)).getOutEdges();
			for (int j = 0; j < disLength; j++) {
				if (!s.get(j)) {
					if (out.contains(Integer.valueOf(j))) {
						int newdist = dist.get(u).intValue() + 1;
						if (newdist < dist.get(j).intValue()) {
							dist.set(j, Integer.valueOf(newdist));
						}
					}
				}
			}
		}

		for (int i = 0; i < disLength; i++) {
			if (dist.get(i).intValue() < Integer.MAX_VALUE) {
				pathNum += dist.get(i).intValue();
			}
		}
		return pathNum;

	}
	public Network randomGraph(int nodeNumber, double probability){
		Network network = new Network();
		Map<Integer, Node> nodeMap = new HashMap<Integer, Node>();
	
		for (int i = 0; i < nodeNumber; i++) {
			Node node = new Node(String.valueOf(i),i);
			nodeMap.put(Integer.valueOf(i), node);
		}
		Random rand=new Random();
		for(int i=0;i<nodeNumber;i++){
			for(int j=i+1;j<nodeNumber;j++){
				if(rand.nextDouble() <= probability){
					nodeMap.get(Integer.valueOf(i)).addOutEdge(Integer.valueOf(j));
					nodeMap.get(Integer.valueOf(j)).addOutEdge(Integer.valueOf(i));
				}
			}
		}
		
		network.setEdgeNumber(nodeMap);
		network.setNodeMap(nodeMap);
		network.setNodeNumber(nodeMap);
		return network;
	}

	
	public Network smallWorld(int nodeNumber, float probability,
			int averageDegree) {
		Network network = new Network();
		Map<Integer, Node> nodeMap = new HashMap<Integer, Node>();
		Map<Integer, Node> newNodeMap = new HashMap<Integer, Node>();
		for (int i = 0; i < nodeNumber; i++) {
			Node node = new Node(String.valueOf(i),i);
			for (int j = 1; j <= averageDegree / 2; j++) {
				node.addOutEdge((Integer.valueOf((i + j) % nodeNumber)));
			}
			for (int j = 1; j <= averageDegree / 2; j++) {
				
				node.addOutEdge((Integer.valueOf((i - j + nodeNumber)% nodeNumber)));
			}
			nodeMap.put(Integer.valueOf(i), node);
		}
/*		
		  System.out.println("-----Regular Lattice begin-----");
		  System.out.println("Regular Lattice network.nodeNumber is " + nodeMap.size()); 
		  System.out.println("nodeNumber is " + nodeNumber);
		  for (int i = 0; i < nodeMap.size(); i++) {
			  System.out.println("node " + i + " neighbors are:"); 
			  LinkedList<Integer> out =nodeMap.get(Integer.valueOf(i)).getOutEdges();
			  for (int j = 0; j < out.size(); j++) {
				  System.out.print(out.get(j).intValue() + "\t"); 
				  }
		      System.out.println(); 
		  } 
		  System.out.println();
		  System.out.println("-----Regular Lattice end-----");
*/		 
		Random random = new Random();
		int ran = 0;
		for (int i = 0; i < nodeNumber; i++) {

			Node newNode;
			Integer key = Integer.valueOf(i);
			if (!newNodeMap.containsKey(key)) {
				newNode = new Node(String.valueOf(i),i);
			} else {
				newNode = newNodeMap.get(key);
			}
			Node node = nodeMap.get(key);
			LinkedList<Integer> outEdges = node.getOutEdges();

			for (int j = 0; j < outEdges.size(); j++) {

			
				nodeMap.get(outEdges.get(j)).deleteOutEdge(key);

				if (Math.random() <= probability) {
					while (i != (ran = random.nextInt(nodeNumber))) {
						Integer subKey = Integer.valueOf(ran);
						if (!outEdges.get(j).equals(subKey)) {

							Node newOutNode;
							if (!newNodeMap.containsKey(subKey)) {
								newOutNode = new Node(String.valueOf(ran),ran);
							
								newOutNode.addOutEdge(key);
								newNodeMap.put(subKey, newOutNode);
							} else {
								if (!newNodeMap.get(subKey).getOutEdges().contains(key)) {
								
									newNodeMap.get(subKey).addOutEdge(key);

								}
							}
							if (!newNode.getOutEdges().contains(subKey)) {
								
								newNode.addOutEdge(subKey);
							}
							break;
						}
					}

				} else {
					if (!newNode.getOutEdges().contains(outEdges.get(j))) {
						
						newNode.addOutEdge(outEdges.get(j));
					}
					Node newOutNode;
					if (!newNodeMap.containsKey(outEdges.get(j))) {
						newOutNode = new Node(String.valueOf(outEdges.get(j)),j);
						
						newOutNode.addOutEdge(key);
						newNodeMap.put(outEdges.get(j), newOutNode);
					} else {

						if (!newNodeMap.get(outEdges.get(j)).getOutEdges()
								.contains(key)) {
							
							newNodeMap.get(outEdges.get(j)).addOutEdge(key);

						}
					}
				}
			}
			newNodeMap.put(key, newNode);
		}

		network.setNodeMap(newNodeMap);
		network.setNodeNumber(newNodeMap);
/*		
		 System.out.println("-----Small World begin-----");
		 System.out.println("network.nodeNumber is " + network.getNodeNumber());
		 System.out.println("nodeNumber is " + nodeNumber); 
		 for (int i = 0; i < newNodeMap.size(); i++) {
		 System.out.println("node " + i + " neighbors are:");
		 LinkedList<Integer> out = newNodeMap.get(Integer.valueOf(i)).getOutEdges();
		 for (int j = 0; j < out.size(); j++) {
		 System.out.print(out.get(j).intValue() + "\t"); 
		 } 
		 System.out.println(); 
		 }
		 System.out.println("-----Small World end-----");
	*/	 
		return network;
	}
	
	public Network AddNewNode(Network network,int NewNodeDegree,int AddNodeNum){
		Map<Integer, Node> nodeMap = network.getNodeMap();
		Iterator<Integer> iterator = nodeMap.keySet().iterator();
		double sum1 = 0,sum2 = 0;
		while (iterator.hasNext()) {			
			sum1 = sum1 +nodeMap.get(iterator.next()).getOutEdges().size();
		}		
		iterator = nodeMap.keySet().iterator();		
		while (iterator.hasNext()) {			
			int Nextnode=iterator.next();
			nodeMap.get(Nextnode).setBegin(sum2);
			nodeMap.get(Nextnode).setStop(sum2+nodeMap.get(Nextnode).getOutEdges().size()/sum1);
			sum2=sum2+nodeMap.get(Nextnode).getOutEdges().size()/sum1;
			nodeMap.get(Nextnode).setFlg(0);
		}				
		Node node = new Node(Integer.toString(AddNodeNum),AddNodeNum);
        for(int j=0;j<NewNodeDegree;j++){
        	int d=0;
        	while(d==0){
        		double s=Math.random();        		
        		iterator = nodeMap.keySet().iterator();
        		while (iterator.hasNext()){        			
        			int Nextnode2=iterator.next();
        			if(s>=nodeMap.get(Nextnode2).getBegin()&&s<nodeMap.get(Nextnode2).getStop()&&nodeMap.get(Nextnode2).getFlg()==0){
        				if (network.isDirected()){
        					nodeMap.get(Nextnode2).addInEdge(AddNodeNum);
        					node.addOutEdge(nodeMap.get(Nextnode2).getId());
        				}
        				else{
        					nodeMap.get(Nextnode2).addOutEdge(AddNodeNum);
        					node.addOutEdge(nodeMap.get(Nextnode2).getId());
        				}                      
                      
        				nodeMap.get(Nextnode2).setFlg(1);                     
                      
        				d=1;
        			}
        		}	
        	}
        }       
        nodeMap.put(AddNodeNum, node);
        
        
        network.setNodeMap(nodeMap);
		network.setNodeNumber(nodeMap);	
		network.setEdgeNumber(nodeMap);

		return network;
	}
	public Network initNetwork(String path) {
		Network network = new Network();
		Map<Integer, Node> nodeMap = new HashMap<Integer, Node>();
		Map<String, Integer> nameToIdMap = new HashMap<String, Integer>();
		boolean isDirected=false;

		if (path.length() > 0) {
			try {
				FileInputStream fr = new FileInputStream(path);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(fr));
				try {

					String line;
					line = br.readLine().trim();
					isDirected = Boolean.parseBoolean(line);
					int nodeNumber = -1;
					while ((line = br.readLine()) != null) {
						if (line.trim().length() > 0) {
							String[] str = line.split(" ");
							String sourceName = str[0];
							String destinationName = str[1];

							if (!nameToIdMap.containsKey(sourceName)
									&& !nameToIdMap.containsKey(destinationName)) {
								int sourceId = ++nodeNumber;
								int destinationId = ++nodeNumber;

								nameToIdMap.put(sourceName,Integer.valueOf(sourceId));
								nameToIdMap.put(destinationName,Integer.valueOf(destinationId));

								Node sourceNode = new Node(sourceName, sourceId);
								Node destinationNode = new Node(destinationName, destinationId);

								if (isDirected) {
									sourceNode.addOutEdge(Integer.valueOf(destinationId));
									destinationNode.addInEdge(Integer.valueOf(sourceId));
								} else {
									sourceNode.addOutEdge(Integer.valueOf(destinationId));
									destinationNode.addOutEdge(Integer.valueOf(sourceId));
								}
								nodeMap.put(Integer.valueOf(sourceId),sourceNode);
								nodeMap.put(Integer.valueOf(destinationId),destinationNode);
							}

							else if (nameToIdMap.containsKey(sourceName)
									&& !nameToIdMap.containsKey(destinationName)) {

								int destinationId = ++nodeNumber;
								nameToIdMap.put(destinationName,Integer.valueOf(destinationId));
								Node destinationNode = new Node(destinationName, destinationId);
								Integer sourceId = nameToIdMap.get(sourceName);
								if (isDirected) {								
									destinationNode.addInEdge(sourceId);
								} else {									
									destinationNode.addOutEdge(sourceId);
								}
								nodeMap.get(sourceId).addOutEdge(Integer.valueOf(destinationId));
								nodeMap.put(Integer.valueOf(destinationId),destinationNode);

							} else if (!nameToIdMap.containsKey(sourceName)
									&& nameToIdMap.containsKey(destinationName)) {

								int sourceId = ++nodeNumber;
								nameToIdMap.put(sourceName,Integer.valueOf(sourceId));
								Node sourceNode = new Node(sourceName, sourceId);
								Integer destinationId = nameToIdMap.get(destinationName);
								if (isDirected) {
									nodeMap.get(destinationId).addInEdge(Integer.valueOf(sourceId));									
								} else {
       								nodeMap.get(destinationId).addOutEdge(Integer.valueOf(sourceId));									
								}
								sourceNode.addOutEdge(destinationId);
								nodeMap.put(Integer.valueOf(sourceId),sourceNode);

							} else if (nameToIdMap.containsKey(sourceName)
									&& nameToIdMap.containsKey(destinationName)) {
								
								Integer sourceId = nameToIdMap.get(sourceName);
								Integer destinationId = nameToIdMap.get(destinationName);
								
								if (isDirected) {									
									nodeMap.get(destinationId).addInEdge(sourceId);
								} else {									
									nodeMap.get(destinationId).addOutEdge(sourceId);
								}
								nodeMap.get(sourceId).addOutEdge(destinationId);
							}
						}
					}
				} finally {
					br.close();
				}
			} catch (IOException ex) {
				System.out.println("Read transaction records failed."
						+ ex.getMessage());
				System.exit(1);
			}
		}

		network.setNodeMap(nodeMap);
		network.setNodeNumber(nodeMap);	
		network.setEdgeNumber(nodeMap);
		network.setDirected(isDirected);
		return network;

	}

	public Network AttachementModel(String path,int AddnodeNumber,int NewNodeDegree){        
		Network network = initNetwork(path);	
		int AddNodeNum = network.getNodeNumber();
		for(int i=0;i<AddnodeNumber;i++){
			network=AddNewNode(network,NewNodeDegree,AddNodeNum+i);
		}
		return network;
	}
}
