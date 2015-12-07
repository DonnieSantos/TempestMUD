using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;

namespace Tempest_Builder
{
	/// <summary>
	/// Summary description for frmAbout.
	/// </summary>
	public class frmAbout : System.Windows.Forms.Form
	{
    private System.Windows.Forms.PictureBox pictureAbout;
		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public frmAbout()
		{
			//
			// Required for Windows Form Designer support
			//
			InitializeComponent();

			//
			// TODO: Add any constructor code after InitializeComponent call
			//
		}

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if(components != null)
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

		#region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
      System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(frmAbout));
      this.pictureAbout = new System.Windows.Forms.PictureBox();
      this.SuspendLayout();
      // 
      // pictureAbout
      // 
      this.pictureAbout.Image = ((System.Drawing.Image)(resources.GetObject("pictureAbout.Image")));
      this.pictureAbout.Location = new System.Drawing.Point(0, 0);
      this.pictureAbout.Name = "pictureAbout";
      this.pictureAbout.Size = new System.Drawing.Size(504, 424);
      this.pictureAbout.TabIndex = 0;
      this.pictureAbout.TabStop = false;
      // 
      // frmAbout
      // 
      this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
      this.ClientSize = new System.Drawing.Size(496, 406);
      this.Controls.Add(this.pictureAbout);
      this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
      this.MaximizeBox = false;
      this.MinimizeBox = false;
      this.Name = "frmAbout";
      this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
      this.Text = "About Tempest Builder";
      this.ResumeLayout(false);

    }
		#endregion
	}
}
