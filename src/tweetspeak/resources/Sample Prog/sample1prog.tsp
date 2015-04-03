#login sample1
  #newsfeed
    #ooti num1 ; #ooti num2
    #ootc response
    #ootc choice
    #oots error = "Not in the choices"

    #like
      #outbox "For integer"
      #outbox "Please Input a 1st number: "
      #inbox num1
      #outbox "Please Input a 2nd number: "
      #inbox num2
      #outbox "\tA: ADDITION\n"
      #outbox "\tB: SUBTRACTION\n"
      #outbox "\tC: MULTIPLICATION\n"
      #outbox "\tD: DIVISION\n"
      #outbox "\tE: MODULO\n"
      #outbox "\tF: Exponent\n"
      #outbox "Select an operation: \n"; #inbox choice

      #tweet (choice == 'A')
        #ootf sum
        #share sum num1 + num2
        #outbox "Sum is = " areFriendsWith sum areFriendsWith "\n"

      #retweet (choice == 'B')
        #ootf difference
        #share difference num1 - num2
        #outbox "Difference is = " areFriendsWith difference areFriendsWith "\n"

      #retweet (choice == 'C')
        #ootf product
        #share product num1 * num2
        #outbox "Product is = " areFriendsWith product areFriendsWith "\n"

      #retweet (choice == 'D')
        #ootf quotient
        #share quotient num1 / num2
        #outbox "Quotient is = " areFriendsWith quotient areFriendsWith "\n"

      #retweet (choice == 'E')
        #ootf modulo
        #share modulo num1 % num2
        #outbox "Remainder is = " areFriendsWith modulo areFriendsWith "\n"

      #retweet (choice == 'F')
        #ootf exponent
        #share exponent num1 ^ num2
        #outbox "Answer is = " areFriendsWith exponent areFriendsWith "\n"

      #reply
        #outbox error
#logout