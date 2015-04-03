#login sample7
  #newsfeed
    #ooti grade

    #outbox "Please Input grade: "
    #inbox grade

    #tweet (grade == 100)
      #outbox "Excellent"

    #retweet (grade >= 90 && grade < 100)
      #outbox "You are still awesome."
      #unfollow
      #outbox "That is great"

    #retweet (grade > 70 && grade < 90)
      #outbox "that is still awesome"
      #follow

    #reply (grade <=70)
      #outbox "You still have a chance"

#logout
