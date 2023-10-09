package com.example.sudokugame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class BoardSudoku(context: Context, attributeSet: AttributeSet) : View(context,attributeSet) {

    private var squareSize = 3
    private var innerSqr = 9
    private var cellSize = 0F

    private var selectedRow = 0
    private var selectedColumn = 0


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
    private val selectedCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#6ead3a")
    }
    private val conflictingCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#efedef")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels = Math.min(widthMeasureSpec,heightMeasureSpec)
        setMeasuredDimension(sizePixels,sizePixels)
    }
    override fun onDraw(canvas: Canvas) {
        cellSize = (width/innerSqr).toFloat()
        fillCells(canvas)
        showLines(canvas)
    }
    private fun fillCells(canvas: Canvas){
        if(selectedRow==-1 || selectedColumn==-1) return
        for(r in 0..innerSqr){
            for(c in 0..innerSqr){
                if(r==selectedRow && c==selectedColumn){
                    fillCell(canvas, r, c, selectedCellPaint)
                }
                else if(r==selectedRow || c==selectedColumn){
                    fillCell(canvas, r, c, conflictingCellPaint)
                }
            }
        }
    }
    private fun fillCell(canvas: Canvas, r:Int, c:Int, paint: Paint){
        canvas.drawRect(c*cellSize, r*cellSize, (c+1)*cellSize, (r+1)*cellSize, paint)
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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when(event.action){
            MotionEvent.ACTION_DOWN -> {
                handleTouchEvent(event.x, event.y)
                true
            }
            else -> false
        }
    }
    private fun handleTouchEvent(x:Float, y:Float){
        selectedRow = (y/cellSize).toInt()
        selectedColumn = (x/cellSize).toInt()
        invalidate()
    }
}