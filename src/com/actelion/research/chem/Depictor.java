/*
* Copyright (c) 1997 - 2015
* Actelion Pharmaceuticals Ltd.
* Gewerbestrasse 16
* CH-4123 Allschwil, Switzerland
*
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* 1. Redistributions of source code must retain the above copyright notice, this
*    list of conditions and the following disclaimer.
* 2. Redistributions in binary form must reproduce the above copyright notice,
*    this list of conditions and the following disclaimer in the documentation
*    and/or other materials provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
* ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*/

package com.actelion.research.chem;

import java.awt.*;
import java.util.ArrayList;

public class Depictor extends AbstractDepictor {
	private static final int MAX_TEXTSIZE = 16;

	private int			mpTextSize,mMaxTextSize;
	private float		mLineWidth;
	private ArrayList<Font>	mFonts;

	public Depictor(StereoMolecule mol) {
		super(mol);
		}


	public Depictor(StereoMolecule mol, int displayMode) {
		super(mol, displayMode);
		}

	
	public void setMaximumTextSize(int maxTextSize) {
		mMaxTextSize = maxTextSize;
		}


	protected void init() {
		super.init();
		mFonts = new ArrayList<Font>();
		mMaxTextSize = MAX_TEXTSIZE;
		mLineWidth = 1.0f;
		}


	protected void drawBlackLine(DepictorLine theLine) {
		((Graphics)mG).drawLine(Math.round(theLine.x1),Math.round(theLine.y1),
		                        Math.round(theLine.x2),Math.round(theLine.y2));
		}


    protected void drawDottedLine(DepictorLine theLine) {
        Stroke stroke = ((Graphics2D)mG).getStroke();
        ((Graphics2D)mG).setStroke(new BasicStroke(
        						mLineWidth,
                                BasicStroke.CAP_ROUND,
                                BasicStroke.JOIN_ROUND,
                                mLineWidth,
                                new float[] {3.0f*mLineWidth},
                                0f));
        ((Graphics)mG).drawLine((int)theLine.x1,(int)theLine.y1,
                                (int)theLine.x2,(int)theLine.y2);
        ((Graphics2D)mG).setStroke(stroke);
        }


    public void drawString(String theString,float x,float y) {
		float strWidth = getStringWidth(theString);
		((Graphics)mG).drawString(theString,Math.round(x-strWidth/2),Math.round(y+1+mpTextSize/3));
		}


	protected void drawPolygon(float[] x, float[] y, int count) {
		int[] px = new int[count];
		int[] py = new int[count];
		for (int i=0; i<count; i++) {
			px[i] = Math.round(x[i]);
			py[i] = Math.round(y[i]);
			}
		((Graphics)mG).drawPolygon(px, py, count);
		((Graphics)mG).fillPolygon(px, py, count);
		}


	protected void fillCircle(float x, float y, float r) {
	    ((Graphics)mG).fillOval(Math.round(x), Math.round(y), Math.round(r), Math.round(r));
		}


	protected float getStringWidth(String theString) {
		return ((Graphics)mG).getFontMetrics().stringWidth(theString);
		}


	public void setTextSize(int theSize) {
		mpTextSize = Math.min(theSize, mMaxTextSize);
		if (((Graphics)mG).getFont().getSize() != mpTextSize) {
			for (int i=0; i<mFonts.size(); i++) {
				if ((mFonts.get(i)).getSize() == mpTextSize) {
				    ((Graphics)mG).setFont(mFonts.get(i));
					return;
					}
				}
			Font newFont = new Font("Helvetica",0, mpTextSize);
			mFonts.add(newFont);
			((Graphics)mG).setFont(newFont);
			}
		}


	public int getTextSize() {
	    return mpTextSize;
	    }


	protected float getLineWidth() {
		return mLineWidth;
		}


	protected void setLineWidth(float lineWidth) {
		if (lineWidth <= 1.5f)
			lineWidth = 1.0f;
		if (mLineWidth != lineWidth) {
			mLineWidth = (float)lineWidth;
			((Graphics2D)mG).setStroke(new BasicStroke((float)lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			}
		}


	public void setColor(Color theColor) {
	    ((Graphics)mG).setColor(theColor);
		}
	}