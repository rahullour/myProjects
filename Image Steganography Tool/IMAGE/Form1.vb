Imports AxWMPLib
Imports System
Imports System.Collections.Generic
Imports System.ComponentModel
Imports System.Data
Imports System.Drawing
Imports System.Linq
Imports System.Text
Imports System.Threading.Tasks
Imports System.Windows.Forms


Public Class Form1


    Dim imageformat As Imaging.ImageFormat
    Public image As Bitmap
    Dim drag As Boolean
    Dim drag1 As Boolean


    Dim mousex As Integer
    Dim mousex1 As Integer

    Dim mousey As Integer
    Dim mousey1 As Integer

    Public Sub New()
        InitializeComponent()
    End Sub
    Private Sub Form1_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        ComboBox1.Items.Add("Select")
        ComboBox1.Items.Add("Bmp")
        ComboBox1.Items.Add("Jpeg")
        ComboBox1.Items.Add("Png")
        ComboBox1.Items.Add("Gif")
        ComboBox1.SelectedIndex = 0

    End Sub

    Private Sub Button8_MouseDown(sender As Object, e As MouseEventArgs) Handles Button8.MouseDown
        drag = True

        mousex = Windows.Forms.Cursor.Position.X - Me.Left

        mousey = Windows.Forms.Cursor.Position.Y - Me.Top
    End Sub

    Private Sub Button8_MouseMove(sender As Object, e As MouseEventArgs) Handles Button8.MouseMove

        If drag Then

            Me.Top = Windows.Forms.Cursor.Position.Y - mousey

            Me.Left = Windows.Forms.Cursor.Position.X - mousex

        End If
    End Sub

    Private Sub Button8_MouseUp(sender As Object, e As MouseEventArgs) Handles Button8.MouseUp
        drag = False

    End Sub

    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles Button1.Click

        TextBox1.Clear()
        TextBox2.Clear()
        TextBox3.Clear()

        Dim openDialog As OpenFileDialog = New OpenFileDialog()
        openDialog.Filter = "Image File (*.PNG) | *.png"
        openDialog.InitialDirectory = "C:\Users\Rahul\Desktop"



        If openDialog.ShowDialog() = DialogResult.OK Then
            TextBox1.Text = openDialog.FileName.ToString()
            PictureBox1.ImageLocation = TextBox1.Text
        End If
        If TextBox1.Text.Length > 0 Then
            Dim img As Bitmap = New Bitmap(TextBox1.Text)
            Label8.Text = img.Width & "x" & img.Height

        End If
    End Sub



    Private Sub Button4_Click_1(sender As Object, e As EventArgs) Handles Button4.Click
        End
    End Sub

    Private Sub AxWindowsMediaPlayer1_Enter(sender As Object, e As EventArgs) Handles AxWindowsMediaPlayer1.Enter
        AxWindowsMediaPlayer1.URL = "Z:\GIT-REPO\GitHub\IMAGE_STEGANOGRAPHY TOOL\IMAGE\Resources\backv.mp4"
        AxWindowsMediaPlayer1.Ctlcontrols.play()

    End Sub

    Private Sub Encode_Click(sender As Object, e As EventArgs) Handles Encode.Click
        If TextBox1.Text.Length > 0 Then


            Dim img As Bitmap = New Bitmap(TextBox1.Text)


            For i As Integer = 0 To img.Width - 1

                For j As Integer = 0 To img.Height - 1
                    Dim pixel As Color = img.GetPixel(i, j)

                    If i < 1 AndAlso j < TextBox3.TextLength Then
                        Console.WriteLine("R = [" & i & "][" & j & "] = " & pixel.R)
                        Console.WriteLine("G = [" & i & "][" & j & "] = " & pixel.G)
                        Console.WriteLine("G = [" & i & "][" & j & "] = " & pixel.B)
                        Dim letter As Char = Convert.ToChar(TextBox3.Text.Substring(j, 1))
                        Dim value As Integer = Convert.ToInt32(letter)
                        Console.WriteLine("letter : " & letter & " value : " & value)
                        value = value + 1
                        img.SetPixel(i, j, Color.FromArgb(pixel.R, pixel.G, value))
                    End If

                    If i = img.Width - 1 AndAlso j = img.Height - 1 Then
                        img.SetPixel(i, j, Color.FromArgb(pixel.R, pixel.G, TextBox3.TextLength))
                    End If
                Next
            Next

            For i As Integer = 100 To img.Width - 1

                For j As Integer = 0 To img.Height - 1
                    Dim pixel As Color = img.GetPixel(i, j)

                    If i < 101 AndAlso j < TextBox2.TextLength Then
                        Console.WriteLine("R = [" & i & "][" & j & "] = " & pixel.R)
                        Console.WriteLine("G = [" & i & "][" & j & "] = " & pixel.G)
                        Console.WriteLine("G = [" & i & "][" & j & "] = " & pixel.B)
                        Dim letter As Char = Convert.ToChar(TextBox2.Text.Substring(j, 1))
                        Dim value As Integer = Convert.ToInt32(letter)
                        Console.WriteLine("letter : " & letter & " value : " & value)
                        value = value + 1
                        img.SetPixel(i, j, Color.FromArgb(pixel.R, pixel.G, value))
                    End If

                    If i = img.Width - 2 AndAlso j = img.Height - 2 Then
                        img.SetPixel(i, j, Color.FromArgb(pixel.R, pixel.G, TextBox2.TextLength))
                    End If
                Next
            Next


            Dim saveFile As SaveFileDialog = New SaveFileDialog()
            saveFile.Filter = "Image Files (*.png) | *.png"
            saveFile.InitialDirectory = "C:\Users\Rahul\Desktop"

            If saveFile.ShowDialog() = DialogResult.OK Then
                TextBox1.Text = saveFile.FileName.ToString()
                PictureBox1.ImageLocation = TextBox1.Text
                img.Save(TextBox1.Text)
            End If
            If TextBox1.Text.Length > 0 And TextBox2.Text.Length > 0 And TextBox3.Text.Length > 0 And saveFile.FileName.ToString().Length > 0 Then
                MsgBox("Encryption Successful!")
            End If
        Else
            MsgBox("Please Select An Image First!")
        End If
        PictureBox1.ImageLocation = ""
        Label8.Text = "Dimensions"
        TextBox1.Text = ""
        TextBox2.Text = ""
        TextBox3.Text = ""

    End Sub

    Private Sub Decode_Click(sender As Object, e As EventArgs) Handles Decode.Click
        If TextBox1.Text.Length > 0 Then


            Dim img As Bitmap = New Bitmap(TextBox1.Text)
            Dim message As String = ""
            Dim lastpixel As Color = img.GetPixel(img.Width - 1, img.Height - 1)
            Dim msgLength As Integer = lastpixel.B

            For i As Integer = 0 To img.Width - 1

                For j As Integer = 0 To img.Height - 1
                    Dim pixel As Color = img.GetPixel(i, j)

                    If i < 1 AndAlso j < msgLength Then
                        Dim value As Integer = pixel.B
                        value = value - 1
                        Dim c As Char = Convert.ToChar(value)
                        Dim letter As String = System.Text.Encoding.ASCII.GetString(New Byte() {Convert.ToByte(c)})
                        message = message & letter
                        img.SetPixel(i, j, Color.FromArgb(pixel.R, pixel.G, value))
                    End If
                Next
            Next



            Dim msg As String = ""
            Dim lpixel As Color = img.GetPixel(img.Width - 2, img.Height - 2)
            Dim msgLen As Integer = lpixel.B

            For i As Integer = 100 To img.Width - 1

                For j As Integer = 0 To img.Height - 1
                    Dim pix As Color = img.GetPixel(i, j)

                    If i < 101 AndAlso j < msgLen Then
                        Dim val As Integer = pix.B
                        val = val - 1
                        Dim c As Char = Convert.ToChar(val)
                        Dim letter As String = System.Text.Encoding.ASCII.GetString(New Byte() {Convert.ToByte(c)})
                        msg = msg & letter
                        img.SetPixel(i, j, Color.FromArgb(pix.R, pix.G, val))
                    End If
                Next
            Next

            If TextBox2.Text = msg Then
                TextBox3.Text = message
                Label2.Text = "ENCODED MESSAGE-->"
                MsgBox("Decryption Successful!")

            Else
                MsgBox("Invalid Key!")
            End If
        Else
            MsgBox("Please Select An Image First!")
        End If

    End Sub

    Private Sub Button6_Click(sender As Object, e As EventArgs) Handles Button6.Click
        Dim filedlg As OpenFileDialog = New OpenFileDialog()
        filedlg.Filter = "Image File (*.BMP) | *.Bmp|Image File (*.JPEG) | *.Jpeg|Image File (*.PNG) | *.png|Image File (*.GIF) | *.gif"




        If filedlg.ShowDialog() = DialogResult.OK Then

            TextBox4.Text = filedlg.FileName.ToString()
            PictureBox1.ImageLocation = TextBox4.Text

        End If
        If TextBox4.Text.Length > 0 Then
            image = New Bitmap(TextBox4.Text)
            Label8.Text = image.Width & "x" & image.Height

        End If

    End Sub

    Private Sub Button7_Click(sender As Object, e As EventArgs)





    End Sub

    Private Sub Button5_Click(sender As Object, e As EventArgs) Handles Button5.Click
        If TextBox4.Text.Length > 0 And ComboBox1.SelectedIndex > 0 Then



            Dim saveFile As SaveFileDialog = New SaveFileDialog()
            If ComboBox1.SelectedIndex = 1 Then
                saveFile.Filter = "Image Files (*.BMP) | *.Bmp"
            ElseIf ComboBox1.SelectedIndex = 2 Then
                saveFile.Filter = "Image Files (*.JPEG) | *.jpeg"
            ElseIf ComboBox1.SelectedIndex = 3 Then
                saveFile.Filter = "Image Files (*.PNG) | *.Png"
            ElseIf ComboBox1.SelectedIndex = 4 Then
                saveFile.Filter = "Image Files (*.Gif) | *.gif"
            End If



            If saveFile.ShowDialog() = DialogResult.OK Then
                TextBox4.Text = saveFile.FileName.ToString()
                PictureBox1.ImageLocation = TextBox4.Text

            End If


            If ComboBox1.SelectedIndex = 1 Then
                imageformat = Imaging.ImageFormat.Bmp
            ElseIf ComboBox1.SelectedIndex = 2 Then
                imageformat = Imaging.ImageFormat.Jpeg
            ElseIf ComboBox1.SelectedIndex = 3 Then
                imageformat = Imaging.ImageFormat.Png
            ElseIf ComboBox1.SelectedIndex = 4 Then
                imageformat = Imaging.ImageFormat.Gif
            End If
            image.Save(TextBox4.Text, imageformat)
            PictureBox1.ImageLocation = ""
            Label8.Text = "Dimensions"

            MsgBox("Conversion Successful!")
            TextBox4.Text = " "
            ComboBox1.SelectedIndex = 0


        ElseIf TextBox4.Text.Length < 1 Then
            MsgBox("Please Select An Image First!")

            ComboBox1.SelectedIndex = 0
            Label8.Text = "Dimensions"
            PictureBox1.ImageLocation = ""
        ElseIf ComboBox1.SelectedIndex < 1 Then
            MsgBox("Please Select Image Output Format First!")

        End If

    End Sub

End Class
