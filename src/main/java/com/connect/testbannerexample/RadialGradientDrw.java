package com.connect.testbannerexample;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.DisplayMetrics;

public class RadialGradientDrw {
    public static Drawable getShader(){

        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShadow());
        return shapeDrawable;
    }

    public static class OvalShadow extends OvalShape {
        private Paint mShadowPaint = new Paint();

        public OvalShadow( ) {

            updateRadialGradient( );
        }

        private void updateRadialGradient( ) {

            DisplayMetrics dismet = Resources.getSystem().getDisplayMetrics();
            int min =   dismet.heightPixels < dismet.widthPixels ? dismet.heightPixels : dismet.widthPixels;

            int max =   dismet.heightPixels > dismet.widthPixels ? dismet.heightPixels : dismet.widthPixels;


            int[] gradientColors = new int[2];
            gradientColors[0] = 0xff723cbe;
            // gradientColors[1] = 0xff401658;
            gradientColors[1] =  0xff301658;
            float[] gradientPositions = new float[2];
            gradientPositions[0] = 0.2f;
            gradientPositions[1] = 0.8f;

            RadialGradient radialGradient =
                    new RadialGradient(min /2, max /2, min, gradientColors,gradientPositions, Shader.TileMode.CLAMP);
            this.mShadowPaint.setShader(radialGradient);
        }

        @Override // android.graphics.drawable.shapes.OvalShape, android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
        public void draw(Canvas canvas, Paint paint) {
            DisplayMetrics dismet = Resources.getSystem().getDisplayMetrics();
            float width = dismet.widthPixels / 2;
            float height = dismet.heightPixels / 2;
            float max = width > height ? width : height;
            canvas.drawCircle(width, height,

                    (float) (max*1.2)

                    , this.mShadowPaint);
            // canvas.drawCircle(width, height,  SHADOW_RADIUS, paint);
        }//*/

        @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
        public void onResize(float f, float f2) {
            super.onResize(f, f2);
            updateRadialGradient( );
        }
    }

}