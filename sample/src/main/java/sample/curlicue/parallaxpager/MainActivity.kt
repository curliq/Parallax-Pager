package sample.curlicue.parallaxpager

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import curlicue.parallaxpager.ParallaxPageTransformer

class MainActivity : AppCompatActivity() {

    private lateinit var viewPagerVp: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPagerVp = findViewById(R.id.activityMain_viewpager_vp)

        viewPagerVp.adapter = MyAdapter(supportFragmentManager)
        viewPagerVp.setPageTransformer(true, ParallaxPageTransformer(R.id.fragmentPage_content_Fl))
    }

}

class MyAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return PageFragment.newInstance(
                Color.parseColor("#1a2735"),
                R.drawable.water,
                Pair("Save Water", Color.parseColor("#ffffff")),
                Pair(
                    "Do it not because it's good for the environment, but because it feels good to you, or, " +
                            "whatever would be convincing enough for you to do it.",
                    Color.parseColor("#bbbbbb")
                ))
            1 -> return PageFragment.newInstance(
                Color.parseColor("#dddddd"),
                R.drawable.babies,
                Pair("Generate Babies", Color.parseColor("#000000")),
                Pair(
                    "A lot of them!\nBy creating more humans you're setting up our species for " +
                            "continuous progress, not that it isn't a bad thing and you should maybe be proud!",
                    Color.parseColor("#444444")
                ))
            2 -> return PageFragment.newInstance(
                Color.parseColor("#75314b"),
                R.drawable.location,
                Pair("Your Location", Color.parseColor("#ffffff")),
                Pair(
                    "Share your location with strangers at real-time!\nPrivacy is in our top 15" +
                            "concerns and we guarantee that we'll try to keep your data safe.",
                    Color.parseColor("#bbbbbb")
                ))
            else -> return null
        }

    }

    override fun getCount(): Int {
        return 3
    }
}

class PageFragment : Fragment() {

    enum class Args(val value: String) {
        COLOR("1"), IMAGE("2"), TITLE("3"), TITLE_COLOR("4"), BODY("5"), BODY_COLOR("6"),
    }

    companion object {

        fun newInstance(color: Int, imageRes: Int, title: Pair<String, Int>, body: Pair<String, Int>): PageFragment {
            val frag = PageFragment()
            val bundle = Bundle()
            bundle.putInt(Args.COLOR.value, color)
            bundle.putInt(Args.IMAGE.value, imageRes)
            bundle.putString(Args.TITLE.value, title.first)
            bundle.putInt(Args.TITLE_COLOR.value, title.second)
            bundle.putString(Args.BODY.value, body.first)
            bundle.putInt(Args.BODY_COLOR.value, body.second)
            frag.arguments = bundle
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_page, container, false)

        val rootFl = v.findViewById<FrameLayout>(R.id.fragmentPage_root_fl)
        val imageIv = v.findViewById<ImageView>(R.id.fragmentPage_image_iv)
        val titleTv = v.findViewById<TextView>(R.id.fragmentPage_title_tv)
        val bodyTv = v.findViewById<TextView>(R.id.fragmentPage_body_tv)

        rootFl.setBackgroundColor(arguments!![Args.COLOR.value] as Int)
        imageIv.setImageResource(arguments!![Args.IMAGE.value] as Int)
        titleTv.text = arguments!![Args.TITLE.value] as String
        titleTv.typeface = Typeface.createFromAsset(activity!!.assets, "fonts/GoogleSans-Bold.ttf")
        titleTv.setTextColor(arguments!![Args.TITLE_COLOR.value] as Int)
        bodyTv.text = arguments!![Args.BODY.value] as String
        bodyTv.typeface = Typeface.createFromAsset(activity!!.assets, "fonts/GoogleSans-Regular.ttf")
        bodyTv.setTextColor(arguments!![Args.BODY_COLOR.value] as Int)

        return v
    }

}