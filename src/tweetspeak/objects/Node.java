package tweetspeak.objects;

import java.util.ArrayList;

import tweetspeak.collections.TokenType;;

public class Node {

	private Node parentNode;
	private ArrayList<Node> child = new ArrayList<Node>();
	private TokenType tokenType;
	private Object value;
	private String tokenData;
	private Token token = null;

	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}

	public Node getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

	public String getTokenData() {
		return tokenData;
	}

	public void setTokenData(String tokenData) {
		this.tokenData = tokenData;
	}
	public ArrayList<Node> getchild() {
		return child;
	}

	public void addChild(Node childNode) {
		child.add(childNode);
	}

	public void addChild(int index, Node childNode) {
		child.add(index, childNode);
	}

	public Node getChild(int index) {
		return child.get(index);
	}

	public void setChild(Node previousChild, Node newChild) {
		child.set(child.indexOf(previousChild), newChild);
	}

	public void setchild(ArrayList<Node> child) {
		this.child = child;
	}

	public TokenType TokenType() {
		return tokenType;
	}

	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		String tree = "Node[" + tokenData + "] Child[";

		for (int ctr = 0; ctr < child.size(); ctr++) {
			if (ctr > 0) {
				tree += ", ";
			}
			tree += child.get(ctr).toString();
		}
		tree += "]";
		return tree;
	}
}