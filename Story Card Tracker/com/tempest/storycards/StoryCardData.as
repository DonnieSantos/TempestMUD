﻿class com.tempest.storycards.StoryCardData
{
	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private var _CardData:Array = new Array();
	private var _CardDesc:String;

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	public function GetCardData():Array { return _CardData; }
	public function GetCardDesc():String { return _CardDesc; }

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////

	public function StoryCardData()
	{
		_CardData[0] = "123456";
		_CardData[1] = "Combat Card";
		_CardData[2] = "Not Started";
		_CardData[3] = "Donnie";
		_CardData[4] = "Feature";
		_CardData[5] = "Feature";
		_CardData[6] = "11/11/2008";
		_CardData[7] = "11/11/2008";
		_CardDesc = "Card Description...";
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////
}