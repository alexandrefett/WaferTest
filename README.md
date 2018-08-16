# Wafer

More detailed comment in code.

Call api to load countries data
Only country name, first language and first currency are loaded in Country model class
Use Volley to call api to fill array.
RecyclerView with CountryAdapter

ItemTouchHelper.SimpleCallback used to handle swipe
onSwipe method -> remove item
onChildDraw -> draw on canvas icon and purple background. set current item position 

