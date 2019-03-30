-Foreign Exchange Arbitrage-
Arbitrage exploits a price di erence between two or more markets to make money with zero risk.  Sporting
bets are perhaps the easiest example.  Given a sporting event with two possible outcomes you look for an
opportunity where two book makers give slightly di erent odds and try to ake bene t of it.  For example,
consider a case with two outcomes, say Federer vs.  Nadal.  One bookie o ers odds of 5/2 on Federer, whereas
another o ers odds of 3/5 on Nadal.  If I bet $32 on 5/2, and $68 at 3/5 then regardless of the outcome
I'm guaranteed to make a little money (a minimum of 9% in this case.  $32 at 5/2 odds gives me $112 and
$68 at 3/5 gives me $109).  Foreign exchange (Forex) markets are another opportunity for the arbitrageur.
By  nding mispriced currencies you can get money for nothing.  For example I could transfer my money
from USD to EURO and using another conversation of currencies back to USD and end up with a pro t if
someone got these exchange rates wrong!  Now all I need to do is  nd a way of  nding these opportunities
and I'll be rich!  The exchange rates can be represented as a weighted graph with each node representing a
currency and each directed edge representing the exchange rate.  To  nd an arbitrage opportunity we need
to  nd a path through the graph (starting and ending at the same node) where the product of edge weights
is greater than 1.  The shortest path is the best one, because we want to make as few trades as possible.  In
this project students will explore the mathematical modeling of this problem using directed weighted graphs,
the existence of negative weighted cycle, and the available shortest path algorithms to solve this problem.

I don't know why a bunch of characters dissapeared when copying but whatever