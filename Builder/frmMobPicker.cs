using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;

namespace Tempest_Builder

{
  public class frmMobPicker : System.Windows.Forms.Form

  {
    private moblist mob_list;
    private mob selected_mob;
    private bool saved;

    #region GUI Variables

    private System.Windows.Forms.Button cmdSelect;
    private System.Windows.Forms.Button cmdCancel;
    private System.Windows.Forms.ListBox lstMobList;
    private System.Windows.Forms.CheckBox chkOutside;
    private System.Windows.Forms.TextBox txtOutsideID;
    private System.Windows.Forms.Label label2;
    private System.ComponentModel.Container components = null;
    #endregion

    public frmMobPicker(moblist ilist)

    {
      mob_list = ilist;
      InitializeComponent();
      chkOutside.Checked = false;
      txtOutsideID.Enabled = false;
    }

    public bool get_saved()

    {
      return saved;
    }

    public moblist get_list()

    {
      return mob_list;
    }

    public mob get_selected()

    {
      return selected_mob;
    }

    public int get_size()

    {
      return mob_list.get_size();
    }

    #region Windows Form Designer generated code
    /// <summary>
    /// Required method for Designer support - do not modify
    /// the contents of this method with the code editor.
    /// </summary>
    private void InitializeComponent()
    {
      System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(frmMobPicker));
      this.lstMobList = new System.Windows.Forms.ListBox();
      this.cmdSelect = new System.Windows.Forms.Button();
      this.cmdCancel = new System.Windows.Forms.Button();
      this.chkOutside = new System.Windows.Forms.CheckBox();
      this.txtOutsideID = new System.Windows.Forms.TextBox();
      this.label2 = new System.Windows.Forms.Label();
      this.SuspendLayout();
      // 
      // lstMobList
      // 
      this.lstMobList.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.lstMobList.Location = new System.Drawing.Point(8, 8);
      this.lstMobList.Name = "lstMobList";
      this.lstMobList.Size = new System.Drawing.Size(336, 171);
      this.lstMobList.TabIndex = 0;
      // 
      // cmdSelect
      // 
      this.cmdSelect.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdSelect.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdSelect.Location = new System.Drawing.Point(88, 208);
      this.cmdSelect.Name = "cmdSelect";
      this.cmdSelect.TabIndex = 1;
      this.cmdSelect.Text = "&Select";
      this.cmdSelect.Click += new System.EventHandler(this.cmdSelect_Click);
      // 
      // cmdCancel
      // 
      this.cmdCancel.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdCancel.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdCancel.Location = new System.Drawing.Point(192, 208);
      this.cmdCancel.Name = "cmdCancel";
      this.cmdCancel.TabIndex = 2;
      this.cmdCancel.Text = "&Cancel";
      this.cmdCancel.Click += new System.EventHandler(this.cmdCancel_Click);
      // 
      // chkOutside
      // 
      this.chkOutside.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkOutside.BackgroundImage")));
      this.chkOutside.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkOutside.Location = new System.Drawing.Point(168, 184);
      this.chkOutside.Name = "chkOutside";
      this.chkOutside.Size = new System.Drawing.Size(88, 16);
      this.chkOutside.TabIndex = 10;
      this.chkOutside.Text = "Outside Mob";
      this.chkOutside.CheckedChanged += new System.EventHandler(this.chkOutside_CheckedChanged);
      // 
      // txtOutsideID
      // 
      this.txtOutsideID.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtOutsideID.Location = new System.Drawing.Point(72, 184);
      this.txtOutsideID.Name = "txtOutsideID";
      this.txtOutsideID.Size = new System.Drawing.Size(88, 20);
      this.txtOutsideID.TabIndex = 9;
      this.txtOutsideID.Text = "";
      // 
      // label2
      // 
      this.label2.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label2.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label2.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label2.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label2.Location = new System.Drawing.Point(8, 184);
      this.label2.Name = "label2";
      this.label2.Size = new System.Drawing.Size(56, 16);
      this.label2.TabIndex = 8;
      this.label2.Text = "ID:";
      // 
      // frmMobPicker
      // 
      this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
      this.BackColor = System.Drawing.SystemColors.Menu;
      this.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("$this.BackgroundImage")));
      this.ClientSize = new System.Drawing.Size(352, 238);
      this.ControlBox = false;
      this.Controls.Add(this.chkOutside);
      this.Controls.Add(this.txtOutsideID);
      this.Controls.Add(this.label2);
      this.Controls.Add(this.cmdCancel);
      this.Controls.Add(this.cmdSelect);
      this.Controls.Add(this.lstMobList);
      this.Name = "frmMobPicker";
      this.ShowInTaskbar = false;
      this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
      this.Text = "Mob Selector";
      this.ResumeLayout(false);

    }
    #endregion

    private void cmdSelect_Click(object sender, System.EventArgs e)

    {

      if (lstMobList.SelectedItem != null)

      {
        selected_mob = mob_list.get_mob(lstMobList.SelectedIndex);
        saved = true;
        this.Hide();
      }

      else if (chkOutside.Checked)

      {
        if (txtOutsideID.Text != "")

        {
          selected_mob = new mob();

          try

          {
            Int32.Parse(txtOutsideID.Text);
          }

          catch (System.FormatException)

          {
            MessageBox.Show("Invalid ID number.");
            return;
          }

          selected_mob.id = txtOutsideID.Text;
          selected_mob.name = "Outside Mob";
          saved = true;
          this.Hide();
        }
      }
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
      selected_mob = null;

      lstMobList.Items.Clear();

      for (int i=0; i<mob_list.get_size(); i++)

      {
        temp  = mob_list.get_mob(i).id;
        temp  = "[" + temp.PadLeft(5, '0') + "] ";
        temp += mob_list.get_mob(i).name;
        lstMobList.Items.Add(temp);
      }

      if (mob_list.get_size() > 0)
        lstMobList.SetSelected(0, true);

      lstMobList.Enabled = true;
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
        lstMobList.Enabled = false;
        txtOutsideID.Enabled = true;
        lstMobList.SelectedItem= null;
      }

      else

      {
        lstMobList.Enabled = true;
        txtOutsideID.Enabled = false;
      }
    }

  }
}
