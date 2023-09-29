package com.example.sudokugame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class BoardSudoku(context: Context, attributeSet: AttributeSet) : View(context,attributeSet) {

    private var squareSize = 3
    private var innerSqr = 9
    private var cellSize = 0F


    private val borderPaint = Paint().apply{
        style = Paint.Style.STROKE
        color = Color.BLACK;
        strokeWidth = 6F
    }
    private val innerPaint = Paint().apply{
        style = Paint.Style.STROKE
        color = Color.BLACK;
        strokeWidth = 3F
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels = Math.min(widthMeasureSpec,heightMeasureSpec)
        setMeasuredDimension(sizePixels,sizePixels)
    }
    override fun onDraw(canvas: Canvas) {
        cellSize = (width/innerSqr).toFloat()
        showLines(canvas)
    }

    private fun showLines(canvas: Canvas) {
        canvas.drawRect(0F,0F,width.toFloat(),height.toFloat(),borderPaint)
        for(i in 1 until innerSqr){
            val lineToUse = when(i % squareSize){
                0 -> borderPaint
                else -> innerPaint
            }

            canvas.drawLine(i*cellSize,0F,i*cellSize,height.toFloat(),lineToUse)
            canvas.drawLine(0F,i*cellSize,width.toFloat(),i*cellSize,lineToUse)
        }
    }
}