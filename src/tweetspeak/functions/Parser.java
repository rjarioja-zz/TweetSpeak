package tweetspeak.functions;

import java.util.*;
import tweetspeak.collections.TokenName;
import tweetspeak.collections.TokenType;
import tweetspeak.divisions.Code;
import tweetspeak.divisions.CodeLine;
import tweetspeak.objects.*;
import tweetspeak.objects.Error;

public class Parser {

	private static int state;
	private static Token cToken;
    private static Stack<Node> node;
    private static Stack<Integer> stateStack;
	
	
	private static void getToken(){
		cToken = Tokenizer.getToken();
	}
	
	public static boolean parser(){
		
		state = 0;
		
		Node node1 = new Node();
	    node1.setTokenData("$");
	    node.push(node1);
	    stateStack.push(state);
		
		//PARSING TABLE IN SWITCH-CASE FORM
		//prepare for pagkahaba habang code :(
		//please ganto nalang
		//sorry
		
		
		while(true)
		{
			String stackTop = node.peek().getTokenData();
            Token tokenTop = node.peek().getToken();
            
			switch(state)
			{
			case 0:
				if(stackTop.equals("<PROGRAM>") && tokenTop == null){
					state = 1;
					stateStack.push(state);
				} else if(cToken.getName() == TokenName.START.toString())
				{
					shift(2);
				} else {
					error();
				} break;
			case 1:
				//di ko sure
				
				break;
			case 2:
				if(cToken.getName() == TokenName.PROG_NAME.toString()){
					shift(3);
				} else{
					//PROG NAME IS REQUIRED
					errorMsg("Program Name");
				} break;
			case 3:
				if(cToken.getName() == "INDENT"){
					shift(4);
				}else{
					errorMsg("an indent.");
				} break;
			case 4:
				if(cToken.getName() == TokenName.MAIN.toString()){
					shift(10);
				} else if(stackTop.equals("<FUNCTIONS>") && tokenTop == null){
					state = 5;
					stateStack.push(state);
				} else if(stackTop.equals("<MAIN_FUNCTION>") && tokenTop == null){
					state = 8;
					stateStack.push(state);
				} else {
					error();
				} break;
			case 5:
				if(cToken.getName() == "DEDENT")
				{
					shift(6);
				} else {
					errorMsg("a dedent.");
				} break;
			case 6:
				if(cToken.getName() == TokenName.END.toString()){
					shift(7);
				} else{
					errorMsg("End of Program.");
				} break;
			case 7:
/* 			IDK HOW? Pano i-identify if $ na
 *				same prob with case 2 
 * 			if($) { 
 *				reduce(2); //state 2 
 * 			} else {
 * 			errorMsg("end of file.");
 * 			} break;
  */
			case 8:
				if(cToken.getName() == "DEDENT"){
					reduce(4);
				} else if(cToken.getName() == TokenName.DATATYPE_INT.toString()){
					shift(28);
				} else if(cToken.getName() == TokenName.DATATYPE_FLOAT.toString()){
					shift(29);
				} else if(cToken.getName() == TokenName.DATATYPE_CHAR.toString()){
					shift(30);
				} else if(cToken.getName() == TokenName.DATATYPE_STRING.toString()){
					shift(31);
				} else if(cToken.getName() == TokenName.DATATYPE_BOOL.toString()){
					shift(32);
				} else if(cToken.getName() == TokenName.DATATYPE_VOID.toString()){
					shift(33);
				} else if(stackTop.equals("<SUB_FUNCTIONS>") && tokenTop == null){
					state = 9;
					stateStack.push(state);
				} else {
					error();
				} break;
			case 9:
				if(cToken.getName() == "DEDENT"){
					reduce(3);
				} else {
					error();
				} break;
			case 10:
				if(cToken.getName() == "INDENT"){
					shift(11);
				} else {
					errorMsg("an indent.");
				} break;
			case 11:
				if(cToken.getName() == TokenName.PROC_CALL.toString()){
					shift(129);
				} else if (cToken.getName() == TokenName.PROC_RET.toString()){
					shift(140);
				} else if(cToken.getName() == TokenName.BREAK.toString()){
					shift(26);
				} else if(cToken.getName() == TokenName.CONTINUE.toString()){
					shift(27);
				} else if(cToken.getName() == TokenName.DATATYPE_INT.toString()){
					shift(28);
				} else if(cToken.getName() == TokenName.DATATYPE_FLOAT.toString()){
					shift(29);
				} else if(cToken.getName() == TokenName.DATATYPE_CHAR.toString()){
					shift(30);
				} else if(cToken.getName() == TokenName.DATATYPE_STRING.toString()){
					shift(31);
				} else if(cToken.getName() == TokenName.DATATYPE_BOOL.toString()){
					shift(32);
				} else if(cToken.getName() == TokenName.DATATYPE_VOID.toString()){
					shift(33);
				} else if (cToken.getName() == TokenName.INPUT.toString()){
					shift(142);
				} else if(cToken.getName() == TokenName.OUTPUT.toString()){
					shift(144);
				} else if(cToken.getName() == TokenName.IF.toString()){
					shift(146);
				} else if(cToken.getName() == TokenName.ELSE_IF.toString()){
					shift(155);
				} else if(stackTop.equals("<DECLARATION>") && tokenTop == null){
					state = 12;
					stateStack.push(state);
				} else if(stackTop.equals("<ASSIGNMENT>") && tokenTop == null){
					state = 13;
					stateStack.push(state);
				} else if(stackTop.equals("<IO>") && tokenTop == null){
					state = 14;
					stateStack.push(state);
				} else if(stackTop.equals("<CONTROL_FLOW>") && tokenTop == null){
					state = 15;
					stateStack.push(state);
				}
			}//end of switch
		}
		
	}
	
	
	private static void reduce(int newState) {
		// TODO Auto-generated method stub
		
	}

	private static void error() {
		// TODO Auto-generated method stub
		//ERROR MESSAGE or sumthin
	}

	private static void errorMsg(String errormsg){
		System.out.println("Error at line " + cToken.getLineNumber() + ". Expecting: " + errormsg);
	}
	
	private static void shift(int newState) {
        Node newNode = new Node();
        newNode.setTokenData(cToken.getType().toString());
        newNode.setToken(cToken);
        node.push(newNode);
        
        state = newState;
        stateStack.push(state);

        getToken();

        System.out.println("-----SHIFT-----");
        System.out.println("'" + newNode.getTokenData() + "' shifted. Shift to state " + newState );
        System.out.println("Current stack contents: " + stateStack);
    }
	
}
