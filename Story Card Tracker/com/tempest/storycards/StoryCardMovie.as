import mx.utils.Delegate;
import mx.transitions.Tween;
import mx.transitions.easing.*;
import com.tempest.storycards.Draw;

class com.tempest.storycards.StoryCardMovie
{
	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	public var _StoryCardMovie:MovieClip;
	private var _HeaderText:TextField;
	private var _BodyText:Array = new Array();
	private var _CloseWindow:MovieClip;
	private var _CloseWindowHover:MovieClip;

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	public function getMovieClip() : MovieClip { return _StoryCardMovie; }

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	public function StoryCardMovie()
	{
		_StoryCardMovie = _root.createEmptyMovieClip("StoryCard", _root.getNextHighestDepth());

		_HeaderText = _StoryCardMovie.createTextField("HeaderText", depth(), 5, 8, 370, 30);
		_BodyText[0] = _StoryCardMovie.createTextField("BodyTextLine1", depth(), 50, 62, 325, 30);
		_BodyText[1] = _StoryCardMovie.createTextField("BodyTextLine2", depth(), 15, 85, 360, 30);
		_BodyText[2] = _StoryCardMovie.createTextField("BodyTextLine3", depth(), 15, 108, 360, 30);
		_BodyText[3] = _StoryCardMovie.createTextField("BodyTextLine4", depth(), 15, 131, 360, 30);
		_BodyText[4] = _StoryCardMovie.createTextField("BodyTextLine5", depth(), 15, 154, 360, 30);
		_BodyText[5] = _StoryCardMovie.createTextField("BodyTextLine6", depth(), 15, 177, 360, 30);
		_BodyText[6] = _StoryCardMovie.createTextField("BodyTextLine7", depth(), 15, 200, 360, 30);
		_BodyText[7] = _StoryCardMovie.createTextField("BodyTextLine8", depth(), 15, 223, 360, 30);

		_CloseWindow = _StoryCardMovie.attachMovie("CloseWindow", "CloseWindow", depth());
		_CloseWindowHover = _StoryCardMovie.attachMovie("CloseWindowHover", "CloseWindowHover", depth());

		drawNoteCard();
		drawHeaderText("Combat Card");
		drawBodyText("Blah Blah Blah");

		_StoryCardMovie._x = 208;
		_StoryCardMovie._y = 166;
		
		_CloseWindow._x = 360;
		_CloseWindow._y = 19;

		_CloseWindowHover._x = 360;
		_CloseWindowHover._y = 19;
		_CloseWindowHover._alpha = 0;
		_CloseWindowHover.onRollOver = Delegate.create(this, CloseWindowRollOver);
		_CloseWindowHover.onRollOut = Delegate.create(this, CloseWindowRollOut);
		_CloseWindowHover.onPress = Delegate.create(this, CloseWindow);

		Mouse.addListener(_CloseWindow);
		Mouse.addListener(_CloseWindowHover);
		
		this.slideIn();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private function depth():Number
	{
		return _StoryCardMovie.getNextHighestDepth();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private function CloseWindow()
	{
		_root["MASK"]._alpha = 0;
		slideOut();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private function CloseWindowRollOver()
	{
		_CloseWindowHover._alpha = 100;
	}
	private function CloseWindowRollOut()
	{
		_CloseWindowHover._alpha = 0;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private function drawNoteCard()
	{
		Draw.solidRectangle(_StoryCardMovie, 0, 0, 384, 269, 0xFFFFFF, 100);
		Draw.line(_StoryCardMovie, 1, 37, 383, 37, 0xff0000, 2, 100);
		Draw.rectangle(_StoryCardMovie, 0, 0, 384, 269, 0x000000, 1, 100);

		for (var i=1; i<=9; i++)
		{
			Draw.line(_StoryCardMovie, 0, (23*i)+37, 384, (23*i)+37, 0x0000ff, 1, 100);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	private function drawHeaderText(headerText:String)
	{
		_HeaderText.text = headerText;
		Draw.FormatText(_HeaderText, "Georgia", 15, true);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private function drawBodyText(bodyText:String)
	{
		var currentLine:Number = 0;
		var lineLength:Number = 35;

	 	while (bodyText.length > 0)
	 	{
			var words:Array = bodyText.split(' ');
			var buffer:String = "";
			var newBodyText:String = "";
			var done:Boolean = false;
	
			for (var i=0; i<words.length; i++)
			{
				var word = words[i];

				if ((done == false) && ((buffer.length + word.length) <= lineLength))
				{
					buffer += word;
					buffer += " ";
			 	}
			 	else
			 	{
					done = true;
					newBodyText += word;
					newBodyText += " ";
			 	}
			}

			_BodyText[currentLine].text = buffer;
			bodyText = newBodyText;
			currentLine++;
			lineLength = 40;
		}

		for (var i=0; i<8; i++)
		{
			Draw.FormatText(_BodyText[i], "Courier New", 14, false);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	public function slideIn()
	{
		var tween:Tween = new Tween(_StoryCardMovie, "_y", Regular.easeOut, 780, 166, 10, false);
		tween = new Tween(_StoryCardMovie, "_x", Regular.easeOut, -406, 208, 10, false);
		tween = new Tween(_StoryCardMovie, "_width", Regular.easeOut, 1, 384, 10, false);
		tween = new Tween(_StoryCardMovie, "_height", Regular.easeOut, 1, 269, 10, false);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private function slideOut()
	{
		var tween:Tween = new Tween(_StoryCardMovie, "_y", Regular.easeIn, 166, 780, 10, false);
		tween = new Tween(_StoryCardMovie, "_x", Regular.easeIn, 208, -406, 10, false);
		tween = new Tween(_StoryCardMovie, "_width", Regular.easeIn, 384, 1, 10, false);
		tween = new Tween(_StoryCardMovie, "_height", Regular.easeIn, 269, 1, 10, false);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////
}