from customtkinter import *
from tkinter import filedialog
from PIL import Image, ImageTk
import numpy as np

def abrir_arquivo():
    file_path = filedialog.askopenfilename(
        filetypes=[("Imagens", ".png .jpg .jpeg .bmp .ico")]
    )
    if file_path:
        print(f"Arquivo selecionado: {file_path}")

        image = Image.open(file_path)

        matriz = np.array(image)
        print(f"Dimensões da matriz: {matriz.shape}")

        width_canvas = canvas.winfo_width()
        height_canvas = canvas.winfo_height()

        if width_canvas == 1 and height_canvas == 1:
            width_canvas = 500
            height_canvas = 400

        image_redimensionada = image.resize((width_canvas, height_canvas), Image.Resampling.LANCZOS)

        img_tk = ImageTk.PhotoImage(image_redimensionada)

        canvas.delete("all")
        canvas.create_image(0, 0, anchor="nw", image=img_tk)
        canvas.image = img_tk 

root = CTk()
root.title("CustomTkinter - Imagem Ajustada")
root.geometry("1200x700")

inputButton = CTkButton(root, text="Abrir Arquivo", command=abrir_arquivo)
inputButton.grid(row=2, column=0, padx=10, pady=10)

canvas = CTkCanvas(root, width=400, height=300)
canvas.grid(row=1, column=0, padx=10, pady=10)

canvas1 = CTkCanvas(root, width=400, height=300)
canvas1.grid(row=1, column=1, padx=20, pady=20)

root.mainloop()
