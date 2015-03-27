#login sample2
  #newsfeed
    #ooti num1 ; #ooti num2
    #ootc response
    #ootc choice
    #oots error = "Not in the choices"
	
    #outbox "For integer"
    #outbox "Please Input a 1st number: "
    #inbox num1
    #outbox "Please Input a 2nd number: "
    #inbox num2
    #outbox "\tA: ADDITION\n"
    #outbox "\tB: MULTIPLICATION\n"
    #outbox "Select an operation: \n"; #inbox choice
	  
    #tweet (choice == 'A')
      #ooti sum #share #trending addition ( num1 , num2 )
      #trending ansAdd ( sum )
    #retweet (choice == 'B')
      #ooti prod #share #trending multiply ( num1 , num2 )
      #trending ansSub ( dif )
    #reply 
      #outbox error
	
    #comment these are method calls	
	#ooti addition( #ooti a , #ooti b )
      #ooti r = a + b
      #throwback r     
	  
    #ootv ansAdd ( #ooti ans )
      #outbox "Sum is = " areFriendsWith ans areFriendsWith "\n"
	  
	#ooti multiply ( #ooti c , #ooti d ) 
      #ooti q = c * d
      #throwback q
	  
    #ootv ansMul ( #ooti ans1)
      #outbox #outbox "Product is = " areFriendsWith ans1 areFriendsWith "\n"	
#logout
