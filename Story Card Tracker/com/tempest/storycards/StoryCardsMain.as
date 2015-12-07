import mx.utils.Delegate;
import com.tempest.storycards.Draw;
import com.tempest.storycards.DataBase;
import com.tempest.storycards.StoryCardMovie;
import com.tempest.storycards.StoryCardRow;
import com.tempest.storycards.StoryCardData;
import com.tempest.storycards.RowHighlighter;

class com.tempest.storycards.StoryCardsMain
{
	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private var _StoryCardsMain:MovieClip;
	private var _MainTitle:TextField;
	private var _HeaderText:Array = new Array();
	private var _HeaderYCoord:Number = 60;
	private var _StoryCardRows:Array = new Array();
	private var _RowHighlighter:RowHighlighter;
	private var _Mask:MovieClip;

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	public function StoryCardsMain()
	{
		_StoryCardsMain = _root.createEmptyMovieClip("", _root.getNextHighestDepth());
		drawBackground();
		drawMainTitle();
		drawHeaderRow();
		drawAllRows();
		_RowHighlighter = new RowHighlighter();
		drawMask();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private function depth():Number
	{
		return _StoryCardsMain.getNextHighestDepth();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private function drawBackground():Void
	{
		Draw.rectangle(_StoryCardsMain, 1, 1, 799, 599, 0x000000, 1, 100);
		Draw.line(_StoryCardsMain, 5, _HeaderYCoord+23, 795, _HeaderYCoord+23, 0x000000, 2, 100);
		Draw.line(_StoryCardsMain, 5, _HeaderYCoord-4, 795, _HeaderYCoord-4, 0x000000, 2, 100);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private function drawAllRows()
	{
		var cardIDs = DataBase.GetCardIDs();

		for (var i=0; i<cardIDs.length; i++)
		{
			_StoryCardRows[i] = new StoryCardRow();
			_StoryCardRows[i].updateText(DataBase.GetStoryCardData(cardIDs[i]));
			_StoryCardRows[i].setYCoordinate(90+(i*20));
			_StoryCardRows[i].getMovieClip().onPress = Delegate.create(this, OpenCard);
			_StoryCardRows[i].getMovieClip().onRollOver = Delegate.create(_StoryCardRows[i], _StoryCardRows[i].Highlight);
			Mouse.addListener(_StoryCardRows[i].getMovieClip());
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private function OpenCard()
	{
		var storyCardMovie = new StoryCardMovie();
		_Mask._alpha = 95;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private function drawMainTitle()
	{
		_MainTitle = _StoryCardsMain.createTextField("", depth(), 295, 13, 400, 80);
		_MainTitle.text = "Story Card Manager";
		Draw.FormatText(_MainTitle, "Verdana", 20, false);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private function drawHeaderRow()
	{
		_HeaderText[0] = _StoryCardsMain.createTextField("", depth(), 10, _HeaderYCoord, 85, 50);
		_HeaderText[1] = _StoryCardsMain.createTextField("", depth(), 65, _HeaderYCoord, 200, 50);
		_HeaderText[2] = _StoryCardsMain.createTextField("", depth(), 280, _HeaderYCoord, 85, 50);
		_HeaderText[3] = _StoryCardsMain.createTextField("", depth(), 380, _HeaderYCoord, 85, 50);
		_HeaderText[4] = _StoryCardsMain.createTextField("", depth(), 460, _HeaderYCoord, 85, 50);
		_HeaderText[5] = _StoryCardsMain.createTextField("", depth(), 540, _HeaderYCoord, 85, 50);
		_HeaderText[6] = _StoryCardsMain.createTextField("", depth(), 630, _HeaderYCoord, 85, 50);
		_HeaderText[7] = _StoryCardsMain.createTextField("", depth(), 710, _HeaderYCoord, 85, 50);

		_HeaderText[0].text = "ID";
		_HeaderText[1].text = "Card Title";
		_HeaderText[2].text = "Status";
		_HeaderText[3].text = "Owner";
		_HeaderText[4].text = "Type";
		_HeaderText[5].text = "Category";
		_HeaderText[6].text = "Created";
		_HeaderText[7].text = "Modified";

		for (var i=0; i<8; i++)
		{
			Draw.FormatText(_HeaderText[i], "Courier New", 15, true);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private function drawMask()
	{
		_Mask = _root.createEmptyMovieClip("MASK", _root.getNextHighestDepth());
		Draw.solidRectangle(_Mask, 2, 2, 798, 598, 0xFFFFFF, 90);
		_Mask._alpha = 0;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////
}