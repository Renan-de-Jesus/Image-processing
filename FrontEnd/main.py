import io
from customtkinter import *
from tkinter import filedialog
from PIL import Image, ImageTk
import requests

url = "http://localhost:8080/api/image/process"

filePath1 = None
filePath2 = None
currentImage1 = None
currentImage2 = None
currentResult = None

def OpenFile(validation):
    global filePath1, filePath2, currentImage1, currentImage2, currentResult
    
    if validation == "1":
        filePath1 = filedialog.askopenfilename(filetypes=[("Imagens", ".png .jpg .jpeg .bmp .tiff .gif")])
        FilePath = filePath1
    elif validation == "2":
        filePath2 = filedialog.askopenfilename(filetypes=[("Imagens", ".png .jpg .jpeg .bmp .tiff .gif")])
        FilePath = filePath2
    else:
        return

    if not FilePath:
        return

    image = Image.open(FilePath)

    if validation == "1":
        currentImage1 = image.copy()
        currentResult = currentImage1.copy()
        canvas = canvas1
    else:
        currentImage2 = image.copy()
        currentResult = currentImage2.copy()
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

def ApplyEffect(effect, value=None):
    global currentImage1, currentImage2, currentResult

    files = {}
    data = {"operation": effect, "value": str(value) if value is not None else ""}

    if effect in ["sum", "subtract", "multiply", "divide"] and currentImage1 and currentImage2:
        buffer1 = io.BytesIO()
        currentImage1.save(buffer1, format="PNG")
        buffer1.seek(0)

        buffer2 = io.BytesIO()
        currentImage2.save(buffer2, format="PNG")
        buffer2.seek(0)

        files["file1"] = ("image1.png", buffer1, "image/png")
        files["file2"] = ("image2.png", buffer2, "image/png")

        try:
            response = requests.post(url, files=files, data=data)
        finally:
            buffer1.close()
            buffer2.close()

    elif currentImage1:
        buffer = io.BytesIO()
        currentImage1.save(buffer, format="PNG")
        buffer.seek(0)

        files["file1"] = ("image.png", buffer, "image/png")

        try:
            response = requests.post(url, files=files, data=data)
        finally:
            buffer.close()
    else:
        print("Nenhuma imagem disponível para aplicar o efeito.")
        return

    if response.status_code == 200:
        img_data = response.content
        img = Image.open(io.BytesIO(img_data))
        img = img.resize((canvasResult.winfo_width(), canvasResult.winfo_height()), Image.Resampling.LANCZOS)
        img_tk = ImageTk.PhotoImage(img)

        canvasResult.delete("all")
        canvasResult.create_image(0, 0, anchor="nw", image=img_tk)
        canvasResult.image = img_tk

        currentResult = img
    else:
        print("Erro:", response.text)


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

OpenFileButton1 = CTkButton(root, text="Abrir Arquivo", command=lambda: OpenFile("1"))
OpenFileButton1.grid(row=2, column=0, padx=10, pady=10)
OpenFileButton2 = CTkButton(root, text="Abrir Arquivo", command=lambda: OpenFile("2"))
OpenFileButton2.grid(row=2, column=1, padx=10, pady=10)

sumButton = CTkButton(root, text="Somar", command=lambda: ApplyEffect("sum", inputSum.get()))
sumButton.grid(row=3, column=0, padx=10, pady=10)
negativeButton = CTkButton(root, text="Negativo", command=lambda: ApplyEffect("negative", 0))
negativeButton.grid(row=4, column=0, padx=10, pady=10)

inputSum = CTkEntry(root, width=40, height=30, placeholder_text="Valor máximo: 255", validate="key", validatecommand=(vcmd, "%P"))
inputSum.grid(row=3, column=1, padx=10, pady=10)

root.mainloop()
