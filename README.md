# Parallax Pager
A viewpager with parallax effect.

### Demo
<img src="https://cdn.discordapp.com/attachments/420735220593983508/536641040644571152/demo.gif" width="250">

### Usage

Since there isn't a lot of code to achieve this you can just create the PageTransformer on your project. It's not on Maven repository yet, I'll update once it is. 

- Create a `PageTransformer` class:
```
class ParallaxPageTransformer(private var pageContentContainerId: Int) : ViewPager.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        val pageContentContainer = view.findViewById<View>(pageContentContainerId)
        when {
            position <= -1 -> { // [-Infinity,-1]
                // Page is way off-screen to the left.
            }
            position <= 0 -> { // ]-1,0]
                // This Prevents shifting at the end
                view.translationX = 0f

                // Move content to the opposite scroll direction in the page that's being scrolled
                pageContentContainer.translationX = -(position * pageContentContainer.width)
            }
            position <= 1 -> { // ]0,1]
                view.translationX = -(position * view.width)
            }
            else -> { // ]1,+Infinity]
                // Page is way off-screen to the right.
            }
        }

    }
}
```

- Initiate it and pass the ID of the viewgroup in your pager fragment that contains all the content but is
not the root viewgroup.
A background color must be applied to the root viewgroup and not to the container passed here.
Example:

(The layout file of the fragments you use in the viewpager)
```
<FrameLayout
    android:id="@+id/fragmentPage_root_fl"
    android:layout_height="match_parent"
    android:layout_width="match_parent"
    xmlns:android="http://schemas.android.com/apk/res/android">
    <LinearLayout
        android:id="@+id/fragmentPage_content_ll"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:weightSum="1"
        android:padding="40dp"
        android:orientation="vertical">
        <TextView
            android:id="@+id/fragmentPage_title_tv"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textSize="30sp"
            android:layout_marginTop="24dp"
            android:layout_marginBottom="12dp"
            android:text="Title text"/>
    </LinearLayout>
</FrameLayout>
```
In fragmentPage_root_fl is where you'd define the background color for each page, and R.id.fragmentPage_content_ll
is what you'd pass when initiating your ParallaxPageTransformer, i.e.:
```
viewPagerVp.adapter = MyAdapter(supportFragmentManager)
viewPagerVp.setPageTransformer(true, ParallaxPageTransformer(R.id.fragmentPage_content_Fl))
```

Full example in `sample/`


