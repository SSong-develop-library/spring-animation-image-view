package com.kr.hkslibrary.springanimationimageview.springanimation

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.view.doOnLayout
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.hk.customcardview.util.dpToPixelFloat

class SpringAnimationImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    // 위로만 , 즉 상 하 로만 뭔가 꺼낸다는 느낌으로?
    private var originalY = 0f // 기존 뷰가 위치하는 y축 좌표

    lateinit var topToBottomAnimation: SpringAnimation // 기존 위치에서 위까지 올라가는 애니메이션

    lateinit var bottomToTopAnimation: SpringAnimation // 위에 올라간 곳에서 기존위치까지 내려가는 애니메이션

    private var deltaY = 0f

    private var translateY = 0f // 변화된 뷰의 위치

    init {
        doOnLayout { // 레이아웃의 상 하의 길이가 필요함 그래서 doOnLayout에서 실행
            originalY = y
            translateY = (y / 1.5).toFloat()
            createTopToBottomAnimation()
            createBottomToTopAnimation()
            setUpTouchListener()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpTouchListener() {
        setOnTouchListener { view, event: MotionEvent? ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    deltaY = view.y - event.rawY // setOnTouch쓸때 뷰의 움직임을 표현하려면 이케 하는데 정확히 왜 하는지는?
                    bottomToTopAnimation.cancel() // 이미지를 눌렀을 떄 animation은 일어나면 안되고

                    translateY = deltaY + (event.rawY - 60.dpToPixelFloat) // 이거 값을 기기에 맞게 조정해야함

                    topToBottomAnimation.start()

                    view.y = translateY
                }
                MotionEvent.ACTION_UP -> {
                    topToBottomAnimation.cancel()
                    bottomToTopAnimation.start() // 이미지를 손에서 떼어냈을때 원래대로 돌아가도록 한다.
                }
            }
            return@setOnTouchListener true
        }
    }

    /**
     * 함수 이름 거꾸로 지었어 이 바보 송훈기;;;
     */

    // 위에서 아래로 내려갈때
    private fun createBottomToTopAnimation() {
        bottomToTopAnimation = SpringAnimation(this, SpringAnimation.Y).apply {
            spring = SpringForce().apply {
                finalPosition = originalY
                stiffness = SpringForce.STIFFNESS_VERY_LOW
                dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
            }
        }
    }

    // 아래서 위로 올라갈떄
    private fun createTopToBottomAnimation() {
        topToBottomAnimation = SpringAnimation(this, SpringAnimation.Y).apply {
            spring = SpringForce().apply {
                finalPosition = translateY
                stiffness = SpringForce.STIFFNESS_LOW
                dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
            }
        }
    }

}