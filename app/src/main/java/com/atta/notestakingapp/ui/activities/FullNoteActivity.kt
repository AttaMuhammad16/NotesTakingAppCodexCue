package com.atta.notestakingapp.ui.activities

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.atta.notestakingapp.R
import com.atta.notestakingapp.util.Utils

class FullNoteActivity : AppCompatActivity() {

    var title=""
    var des=""
    var date=""
    var color=0
    lateinit var date_tv: TextView
    lateinit var title_tv: TextView
    lateinit var des_tv: TextView
    lateinit var constraint: ConstraintLayout
    lateinit var scroll: ScrollView
    lateinit var copyContent: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_note)
        window.statusBarColor=ContextCompat.getColor(this,R.color.tool_bar_color)

        date_tv=findViewById(R.id.date_tv)
        title_tv=findViewById(R.id.title_tv)
        des_tv=findViewById(R.id.des_tv)
        constraint=findViewById(R.id.constraint)
        scroll=findViewById(R.id.scroll)
        copyContent=findViewById(R.id.copyContent)

        val toolBar=findViewById<Toolbar>(R.id.toolBar)
        val backArrowImg = findViewById<ImageView>(R.id.backArrowImg)

        title=intent.getStringExtra("title").toString()
        des=intent.getStringExtra("des").toString()
        date=intent.getStringExtra("date").toString()
        color=intent.getIntExtra("color",-1)
        val index=intent.getIntExtra("index",-1)

        if(color!=-1 && index!=-1){
            window.statusBarColor = color
            constraint.setBackgroundColor(color)
            scroll.setBackgroundColor(color)
            toolBar.setBackgroundColor(color)
        }

        date_tv.text=date
        title_tv.text=title
        des_tv.text=des

        backArrowImg.setOnClickListener {
            finish()
        }

        copyContent.setOnClickListener {
            Utils.rippleEffect(this,copyContent)
            val data=date+"\n"+title+"\n"+des
            Utils.copyContentText(data,this)
            Toast.makeText(this,"Text copied to clipboard", Toast.LENGTH_SHORT).show()

            val zoomInAnimation = ScaleAnimation(1f, 1.2f, 1f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            zoomInAnimation.duration = 300
            zoomInAnimation.fillAfter = true
            zoomInAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    val zoomOutAnimation = ScaleAnimation(1.2f, 1f, 1.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
                    zoomOutAnimation.duration = 300
                    zoomOutAnimation.fillAfter = true
                    copyContent.startAnimation(zoomOutAnimation)
                }
                override fun onAnimationRepeat(animation: Animation) {}
            })
            copyContent.startAnimation(zoomInAnimation)
        }
    }
}