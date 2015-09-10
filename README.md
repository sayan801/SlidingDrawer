We are facing lots of issue with this implementation.. So we are trying to implement a new control based on AndroidSlidingDrawer ( this is also going to deprecate from API 17)..

This approach is Inspired from discussion found in this blog...

http://blog.sephiroth.it/2011/03/29/widget-slidingdrawer-top-to-bottom/


Below mentioned code will be maintained as seperate branch dfqin-SlidingDrawer

SlidingDrawer
=============

A custom slide drawer widget support low api level 

Though there is a SlidingDrawer widget in android SDK which is  deprecated in API level 17, it can't meet the demands in 
many cases and it hard to extend.  

In my case I need a sliding drawer which support drag to top,drag to center or to bottom, so I write this custom 
SlidingDrawer widget, you can drag or click to operate the drawer.

It use "property animation"  do the animation, to support the low API level, I use the NineOldAndroids compatible 
package (download in http://nineoldandroids.com/).


