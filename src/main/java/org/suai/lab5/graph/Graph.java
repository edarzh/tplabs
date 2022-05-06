package org.suai.lab5.graph;

public interface Graph {

	void connect(int vertex1, int vertex2);

	void disconnect(int vertex1, int vertex2);

	int getEdge(int vertex1, int vertex2);

	int getSize();

	void makeGraph(String filePath);
}