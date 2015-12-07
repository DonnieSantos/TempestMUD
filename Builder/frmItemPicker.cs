using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;

namespace Tempest_Builder

{
  public class frmItemPicker : System.Windows.Forms.Form

  {
    private itemlist item_list;
    private item selected_item;
    private string freq;
    private bool saved;

    #region GUI Variables
    private System.Windows.Forms.ListBox lstItemList;
    private System.Windows.Forms.Button cmdSelect;
    private System.Windows.Forms.Button cmdCancel;
    private System.Windows.Forms.TextBox txtFreq;
    private System.Windows.Forms.Label label1;
    private System.Windows.Forms.Label label2;
    private System.Windows.Forms.TextBox txtOutsideID;
    private System.Windows.Forms.CheckBox chkOutside;
    private System.ComponentModel.Container components = null;
    #endregion

    public frmItemPicker(itemlist ilist)

    {
      item_list = ilist;
      InitializeComponent();
      chkOutside.Checked = false;
      txtOutsideID.Enabled = false;
    }

    public string get_freq()

    {
      return freq;
    }

    public bool get_saved()

    {
      return saved;
    }

    public itemlist get_list()

    {
      return item_list;
    }

    public item get_selected()

    {
      return selected_item;
    }

    public int get_size()

    {
      return item_list.get_size();
    }

    public void disable_freq()

    {
      txtFreq.ReadOnly = true;
    }

    public void enable_freq()

    {
      txtFreq.ReadOnly = false;
    }

    #region Windows Form Designer generated code
    /// <summary>
    /// Required method for Designer support - do not modify
    /// the contents of this method with the code editor.
    /// </summary>
    private void InitializeComponent()
    {
      System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(frmItemPicker));
      this.lstItemList = new System.Windows.Forms.ListBox();
      this.cmdSelect = new System.Windows.Forms.Button();
      this.cmdCancel = new System.Windows.Forms.Button();
      this.txtFreq = new System.Windows.Forms.TextBox();
      this.label1 = new System.Windows.Forms.Label();
      this.label2 = new System.Windows.Forms.Label();
      this.txtOutsideID = new System.Windows.Forms.TextBox();
      this.chkOutside = new System.Windows.Forms.CheckBox();
      this.SuspendLayout();
      // 
      // lstItemList
      // 
      this.lstItemList.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.lstItemList.Location = new System.Drawing.Point(8, 8);
      this.lstItemList.Name = "lstItemList";
      this.lstItemList.Size = new System.Drawing.Size(336, 171);
      this.lstItemList.TabIndex = 0;
      // 
      // cmdSelect
      // 
      this.cmdSelect.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdSelect.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdSelect.Location = new System.Drawing.Point(80, 232);
      this.cmdSelect.Name = "cmdSelect";
      this.cmdSelect.TabIndex = 1;
      this.cmdSelect.Text = "&Select";
      this.cmdSelect.Click += new System.EventHandler(this.cmdSelect_Click);
      // 
      // cmdCancel
      // 
      this.cmdCancel.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdCancel.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdCancel.Location = new System.Drawing.Point(184, 232);
      this.cmdCancel.Name = "cmdCancel";
      this.cmdCancel.TabIndex = 2;
      this.cmdCancel.Text = "&Cancel";
      this.cmdCancel.Click += new System.EventHandler(this.cmdCancel_Click);
      // 
      // txtFreq
      // 
      this.txtFreq.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtFreq.Location = new System.Drawing.Point(72, 184);
      this.txtFreq.Name = "txtFreq";
      this.txtFreq.Size = new System.Drawing.Size(88, 20);
      this.txtFreq.TabIndex = 3;
      this.txtFreq.Text = "";
      // 
      // label1
      // 
      this.label1.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label1.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label1.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label1.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label1.Location = new System.Drawing.Point(8, 184);
      this.label1.Name = "label1";
      this.label1.Size = new System.Drawing.Size(56, 16);
      this.label1.TabIndex = 4;
      this.label1.Text = "Freq:";
      // 
      // label2
      // 
      this.label2.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label2.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label2.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label2.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label2.Location = new System.Drawing.Point(8, 208);
      this.label2.Name = "label2";
      this.label2.Size = new System.Drawing.Size(56, 16);
      this.label2.TabIndex = 5;
      this.label2.Text = "ID:";
      // 
      // txtOutsideID
      // 
      this.txtOutsideID.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtOutsideID.Location = new System.Drawing.Point(72, 208);
      this.txtOutsideID.Name = "txtOutsideID";
      this.txtOutsideID.Size = new System.Drawing.Size(88, 20);
      this.txtOutsideID.TabIndex = 6;
      this.txtOutsideID.Text = "";
      // 
      // chkOutside
      // 
      this.chkOutside.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkOutside.BackgroundImage")));
      this.chkOutside.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkOutside.Location = new System.Drawing.Point(168, 184);
      this.chkOutside.Name = "chkOutside";
      this.chkOutside.Size = new System.Drawing.Size(88, 16);
      this.chkOutside.TabIndex = 7;
      this.chkOutside.Text = "Outside Item";
      this.chkOutside.CheckedChanged += new System.EventHandler(this.chkOutside_CheckedChanged);
      // 
      // frmItemPicker
      // 
      this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
      this.BackColor = System.Drawing.SystemColors.Menu;
      this.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("$this.BackgroundImage")));
      this.ClientSize = new System.Drawing.Size(352, 262);
      this.ControlBox = false;
      this.Controls.Add(this.chkOutside);
      this.Controls.Add(this.txtOutsideID);
      this.Controls.Add(this.label2);
      this.Controls.Add(this.label1);
      this.Controls.Add(this.txtFreq);
      this.Controls.Add(this.cmdCancel);
      this.Controls.Add(this.cmdSelect);
      this.Controls.Add(this.lstItemList);
      this.Name = "frmItemPicker";
      this.ShowInTaskbar = false;
      this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
      this.Text = "Item Selector";
      this.ResumeLayout(false);

    }
    #endregion

    private void cmdSelect_Click(object sender, System.EventArgs e)

    {
      try 
      
      {
        int i = Int32.Parse(txtFreq.Text);
        
        if ((i < 0) || (i > 65000))
          throw new System.Exception();
      }
      
      catch (System.FormatException)
      
      {
        MessageBox.Show("Invalid Frequency.");
        return;
      }
      
      catch (System.Exception)
      
      {
        MessageBox.Show("Frequency out of range(0 - 65,000).");
        return;
      }
      
      if (lstItemList.SelectedItem != null)

      {
        selected_item = item_list.get_item(lstItemList.SelectedIndex);
        saved = true;
        freq = txtFreq.Text;
        this.Hide();
      }

      else if (chkOutside.Checked)

      {
        if (txtOutsideID.Text != "")

        {
          selected_item = new item();

          try

          {
            Int32.Parse(txtOutsideID.Text);
          }

          catch (System.FormatException)

          {
            MessageBox.Show("Invalid ID number.");
            return;
          }

          selected_item.id = txtOutsideID.Text;
          selected_item.name = "Outside Item";
          saved = true;
          freq = txtFreq.Text;
          this.Hide();
        }

        else
          MessageBox.Show ("No outside ID number.", "Selection Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
      }

      else
        MessageBox.Show ("No item selected.", "Selection Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
    }

    private void cmdCancel_Click(object sender, System.EventArgs e)

    {
      saved = false;
      this.Hide();
    }

    protected override void OnPaint(PaintEventArgs e)

    {
      string temp;
      saved = false;
      selected_item = null;

      lstItemList.Items.Clear();

      for (int i=0; i<item_list.get_size(); i++)

      {
        temp  = item_list.get_item(i).id;
        temp  = "[" + temp.PadLeft(5, '0') + "] ";
        temp += item_list.get_item(i).name;
        lstItemList.Items.Add(temp);
      }

      if (item_list.get_size() > 0)
        lstItemList.SetSelected(0, true);

      txtFreq.Text = "0";
      lstItemList.Enabled = true;
      chkOutside.Checked = false;
      txtOutsideID.Text = "";
    }

    protected override void Dispose( bool disposing )

    {
      if( disposing )
        if(components != null)
          components.Dispose();

      base.Dispose( disposing );
    }

    private void chkOutside_CheckedChanged(object sender, System.EventArgs e)

    {
      if (chkOutside.Checked)

      {
        lstItemList.Enabled = false;
        txtOutsideID.Enabled = true;
        lstItemList.SelectedItem = null;
      }

      else

      {
        lstItemList.Enabled = true;
        txtOutsideID.Enabled = false;
      }
    }

  }
}
