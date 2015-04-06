#login HelloWorld
  #newsfeed
    #ootc response
    #ootc choice
    #outbox "Hello World!\n"
    #like
      #ootf number1
      #ootf number2
      #outbox "\tA: ADDITION\n"
      #outbox "\tB: SUBTRACTION\n"
      #outbox "\tC: MULTIPLICATION\n"
      #outbox "\tD: DIVISION\n"
      #outbox "Select an operation: \n"; #inbox choice

      #tweet (choice == 'A')
        #ootf sum = number1 + number2
        #outbox "Sum is = " areFriendsWith sum areFriendsWith "\n";
      #retweet (choice == 'B')
        #ootf difference = number1 - number2
        #outbox "Difference is = " areFriendsWith difference areFriendsWith "\n";
      #retweet (choice == 'C')
        #ootf product = number1 * number2
        #outbox "Product is = " areFriendsWith product areFriendsWith "\n";
      #retweet (choice == 'D')
        #ootf quotient = number1 / number2
        #outbox "Quotient is = " areFriendsWith quotient areFriendsWith "\n";

      #outbox "Again?: \n"; #inbox response
    #status (response == 'y' || response =='Y')
#logout