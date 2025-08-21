import io
from customtkinter import *
from tkinter import filedialog
from PIL import Image, ImageTk
import numpy as np
import requests

url = "http://localhost:8080/api/image/process"
FilePath1 = None
FilePath2 = None

def OpenFile(validation):
    global FilePath1, FilePath2
    if validation == "1":
        FilePath1 = filedialog.askopenfilename(
            filetypes=[("Imagens", ".png .jpg .jpeg .bmp .tiff .gif")]
        )
        FilePath = FilePath1
    elif validation == "2":
        FilePath2 = filedialog.askopenfilename(
            filetypes=[("Imagens", ".png .jpg .jpeg .bmp .tiff .gif")]
        )
        FilePath = FilePath2
    else:
        return

    if not FilePath:
        return

    print(f"Arquivo selecionado: {FilePath}")

    image = Image.open(FilePath)
    matriz = np.array(image)
    print(f"Dimensões da matriz: {matriz.shape}")

    if validation == "1":
        canvas = canvas1
    else:
        canvas = canvas2

    width_canvas = canvas.winfo_width()
    height_canvas = canvas.winfo_height()
    if width_canvas <= 1 or height_canvas <= 1:
        width_canvas = 400
        height_canvas = 300

    image_redimensionada = image.resize((width_canvas, height_canvas), Image.Resampling.LANCZOS)
    img_tk = ImageTk.PhotoImage(image_redimensionada)

    canvas.delete("all")
    canvas.create_image(0, 0, anchor="nw", image=img_tk)
    canvas.image = img_tk

def ValidateNumberInput(P):
    if not P:
        return True
    if P.isdigit():
        return int(P) <= 255
    return False

def ApplyEffect(effect, value):
    global FilePath1, FilePath2

    if not FilePath1 and not FilePath2:
        print("Nenhuma imagem selecionada.")
        return

    files = {}
    if FilePath1:
        files["file1"] = open(FilePath1, "rb")
    if FilePath2:
        files["file2"] = open(FilePath2, "rb")

    data = {"operation": effect, "value": str(value)}

    try:
        response = requests.post(url, files=files, data=data)

        if response.status_code == 200:
            img_data = response.content
            img = Image.open(io.BytesIO(img_data))
            img = img.resize((canvasResult.winfo_width(), canvasResult.winfo_height()), Image.Resampling.LANCZOS)
            img_tk = ImageTk.PhotoImage(img)
            canvasResult.delete("all")
            canvasResult.create_image(0, 0, anchor="nw", image=img_tk)
            canvasResult.image = img_tk
        else:
            print("Erro:", response.text)
    finally:
        for f in files.values():
            f.close()

root = CTk()
root.title("CustomTkinter - Imagem Ajustada")
root.geometry("1320x700")

vcmd = root.register(ValidateNumberInput)

canvas1 = CTkCanvas(root, width=400, height=300)
canvas1.grid(row=1, column=0, padx=20, pady=20)

canvas2 = CTkCanvas(root, width=400, height=300)
canvas2.grid(row=1, column=1, padx=20, pady=20)

canvasResult = CTkCanvas(root, width=400, height=300)
canvasResult.grid(row=1, column=2, padx=20, pady=20)

OpenFileButton = CTkButton(root, text="Abrir Arquivo", command=lambda: OpenFile("1"))
OpenFileButton.grid(row=2, column=0, padx=10, pady=10)

OpenFileButton2 = CTkButton(root, text="Abrir Arquivo", command=lambda: OpenFile("2"))
OpenFileButton2.grid(row=2, column=1, padx=10, pady=10)

sumButton = CTkButton(root, text="Somar", command=lambda: ApplyEffect("sum", inputSum.get()))
sumButton.grid(row=3, column=0, padx=10, pady=10)

inputSum = CTkEntry(root, width=40, height=30, placeholder_text="Valor máximo: 255", validate="key", validatecommand=(vcmd, "%P"))
inputSum.grid(row=3, column=1, padx=10, pady=10)

root.mainloop()
