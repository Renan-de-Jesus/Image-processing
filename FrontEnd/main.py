import io
from customtkinter import *
from tkinter import filedialog
from PIL import Image, ImageTk
import requests

URL_API = "http://localhost:8080/api/image/process"
CANVAS_WIDTH = 400
CANVAS_HEIGHT = 300
WINDOW_WIDTH = 1320
WINDOW_HEIGHT = 830

filePath1 = None
filePath2 = None
currentImage1 = None
currentImage2 = None
currentResult = None

def OpenFile(validation):
    """Abre um arquivo de imagem e exibe no canvas apropriado"""
    global filePath1, filePath2, currentImage1, currentImage2, currentResult
    
    if validation == "1":
        filePath1 = filedialog.askopenfilename(
            filetypes=[("Imagens", ".png .jpg .jpeg .bmp .tiff .gif")]
        )
        FilePath = filePath1
        canvas = canvas1
    elif validation == "2":
        filePath2 = filedialog.askopenfilename(
            filetypes=[("Imagens", ".png .jpg .jpeg .bmp .tiff .gif")]
        )
        FilePath = filePath2
        canvas = canvas2
    else:
        return

    if not FilePath:
        return

    image = Image.open(FilePath)

    if validation == "1":
        currentImage1 = image.copy()
        currentResult = currentImage1.copy()
    else:
        currentImage2 = image.copy()
        currentResult = currentImage2.copy()

    DisplayImageOnCanvas(image, canvas)


def DisplayImageOnCanvas(image, canvas):
    width_canvas = canvas.winfo_width()
    height_canvas = canvas.winfo_height()
    
    if width_canvas <= 1 or height_canvas <= 1:
        width_canvas = CANVAS_WIDTH
        height_canvas = CANVAS_HEIGHT

    image_redimensionada = image.resize(
        (width_canvas, height_canvas), 
        Image.Resampling.LANCZOS
    )
    img_tk = ImageTk.PhotoImage(image_redimensionada)

    canvas.delete("all")
    canvas.create_image(0, 0, anchor="nw", image=img_tk)
    canvas.image = img_tk


def SaveFile(filename):
    """Salva a imagem resultado"""
    global currentResult
    
    if currentResult is None:
        print("Nenhum resultado para salvar.")
        return
    
    file_path = filedialog.asksaveasfilename(
        defaultextension=".png",
        filetypes=[("PNG files", "*.png"), ("All files", "*.*")]
    )
    
    if file_path:
        currentResult.save(file_path)


def ApplyEffect(effect, value=None):
    """Aplica um efeito na(s) imagem(ns) através da API"""
    global currentImage1, currentImage2, currentResult

    operations_with_two_images = ["sum", "subtraction", "multiply", "divide", "difference", "blending", "media"]
    
    files = {}
    data = {
        "operation": effect,
        "value": str(value) if value is not None else ""
    }

    if effect in operations_with_two_images and currentImage1 and currentImage2:
        files = PrepareTwoImages(currentImage1, currentImage2)
    elif currentImage1:
        files = PrepareOneImage(currentImage1)
    else:
        print("Nenhuma imagem disponível para aplicar o efeito.")
        return

    try:
        response = requests.post(URL_API, files=files, data=data)
        
        if response.status_code == 200:
            DisplayResultImage(response.content)
        else:
            print("Erro:", response.text)
    except Exception as e:
        print(f"Erro ao processar imagem: {e}")
    finally:
        for file_tuple in files.values():
            if len(file_tuple) > 1:
                file_tuple[1].close()


def PrepareOneImage(image):
    buffer = io.BytesIO()
    image.save(buffer, format="PNG")
    buffer.seek(0)
    return {"file1": ("image.png", buffer, "image/png")}


def PrepareTwoImages(image1, image2):
    buffer1 = io.BytesIO()
    image1.save(buffer1, format="PNG")
    buffer1.seek(0)

    buffer2 = io.BytesIO()
    image2.save(buffer2, format="PNG")
    buffer2.seek(0)

    return {
        "file1": ("image1.png", buffer1, "image/png"),
        "file2": ("image2.png", buffer2, "image/png")
    }


def DisplayResultImage(img_data):
    global currentResult
    
    img = Image.open(io.BytesIO(img_data))
    img_resized = img.resize(
        (canvasResult.winfo_width(), canvasResult.winfo_height()),
        Image.Resampling.LANCZOS
    )
    img_tk = ImageTk.PhotoImage(img_resized)

    canvasResult.delete("all")
    canvasResult.create_image(0, 0, anchor="nw", image=img_tk)
    canvasResult.image = img_tk

    currentResult = img


def ValidateNumberInput(P):
    if not P:
        return True
    if P.isdigit():
        return int(P) <= 255
    return False


# ==================== INTERFACE GRÁFICA ====================
root = CTk()
root.title("Image Processor")
root.geometry(f"{WINDOW_WIDTH}x{WINDOW_HEIGHT}")

vcmd = root.register(ValidateNumberInput)

# ========== LABELS DOS CANVAS (Linha 0) ==========
labelImage1 = CTkLabel(root, text="Imagem 1", font=("Arial", 14, "bold"))
labelImage1.grid(row=0, column=0, padx=20, pady=(10, 0))

labelImage2 = CTkLabel(root, text="Imagem 2", font=("Arial", 14, "bold"))
labelImage2.grid(row=0, column=1, padx=20, pady=(10, 0))

labelResult = CTkLabel(root, text="Resultado", font=("Arial", 14, "bold"))
labelResult.grid(row=0, column=2, padx=20, pady=(10, 0))

# ========== CANVAS (Linha 1) ==========
canvas1 = CTkCanvas(root, width=CANVAS_WIDTH, height=CANVAS_HEIGHT)
canvas1.grid(row=1, column=0, padx=20, pady=(5, 10))

canvas2 = CTkCanvas(root, width=CANVAS_WIDTH, height=CANVAS_HEIGHT)
canvas2.grid(row=1, column=1, padx=20, pady=(5, 10))

canvasResult = CTkCanvas(root, width=CANVAS_WIDTH, height=CANVAS_HEIGHT)
canvasResult.grid(row=1, column=2, padx=20, pady=(5, 10))

# ========== BOTÕES DE ARQUIVO (Linha 2) ==========
OpenFileButton1 = CTkButton(
    root, text="Abrir Imagem 1", 
    command=lambda: OpenFile("1"),
    width=200
)
OpenFileButton1.grid(row=2, column=0, padx=20, pady=5)

OpenFileButton2 = CTkButton(
    root, text="Abrir Imagem 2", 
    command=lambda: OpenFile("2"),
    width=200
)
OpenFileButton2.grid(row=2, column=1, padx=20, pady=5)

SaveFileButton = CTkButton(
    root, text="Salvar Resultado", 
    command=lambda: SaveFile("result.png"),
    width=200
)
SaveFileButton.grid(row=2, column=2, padx=20, pady=5)

# ========== FRAME DE CONTROLES (Linha 3) ==========
controlFrame = CTkFrame(root)
controlFrame.grid(row=3, column=0, columnspan=3, padx=20, pady=20, sticky="ew")

labelArithmetic = CTkLabel(controlFrame, text="Operações Aritméticas", font=("Arial", 12, "bold"))
labelArithmetic.grid(row=0, column=0, columnspan=2, pady=(10, 5))

inputValue = CTkEntry(
    controlFrame, width=150, height=30, 
    placeholder_text="Valor (0-255)",
    validate="key", 
    validatecommand=(vcmd, "%P")
)
inputValue.grid(row=1, column=0, columnspan=2, padx=10, pady=5)

sumButton = CTkButton(
    controlFrame, text="Somar", 
    command=lambda: ApplyEffect("sum", inputValue.get()),
    width=150
)
sumButton.grid(row=2, column=0, padx=5, pady=5)

subtractionButton = CTkButton(
    controlFrame, text="Subtrair", 
    command=lambda: ApplyEffect("subtraction", inputValue.get()),
    width=150
)
subtractionButton.grid(row=2, column=1, padx=5, pady=5)

multiplicationButton = CTkButton(
    controlFrame, text="Multiplicar", 
    command=lambda: ApplyEffect("multiplication", inputValue.get()),
    width=150
)
multiplicationButton.grid(row=3, column=0, padx=5, pady=5)

divisionButton = CTkButton(
    controlFrame, text="Dividir", 
    command=lambda: ApplyEffect("division", inputValue.get()),
    width=150
)
divisionButton.grid(row=3, column=1, padx=5, pady=5)

separator1 = CTkLabel(controlFrame, text="", height=20)
separator1.grid(row=4, column=0, columnspan=2)

labelTransform = CTkLabel(controlFrame, text="Transformações", font=("Arial", 12, "bold"))
labelTransform.grid(row=5, column=0, columnspan=2, pady=(5, 5))

negativeButton = CTkButton(
    controlFrame, text="Negativo", 
    command=lambda: ApplyEffect("negative", 0),
    width=150
)
negativeButton.grid(row=6, column=0, padx=5, pady=5)

grayscaleButton = CTkButton(
    controlFrame, text="Escala de Cinza", 
    command=lambda: ApplyEffect("grayscale", 0),
    width=150
)
grayscaleButton.grid(row=6, column=1, padx=5, pady=5)

flipHorizontalButton = CTkButton(
    controlFrame, text="Flip Horizontal", 
    command=lambda: ApplyEffect("fliplr", 0),
    width=150
)
flipHorizontalButton.grid(row=7, column=0, padx=5, pady=5)

flipVerticalButton = CTkButton(
    controlFrame, text="Flip Vertical", 
    command=lambda: ApplyEffect("flipud", 0),
    width=150
)
flipVerticalButton.grid(row=7, column=1, padx=5, pady=(5, 10))

differenceButton = CTkButton(
    controlFrame, text="Diferença", 
    command=lambda: ApplyEffect("difference", 0),
    width=150
)
differenceButton.grid(row=8, column=0, padx=5, pady=(5, 10))

blendingButton = CTkButton(
    controlFrame, text="Blending", 
    command=lambda: ApplyEffect("blending", inputValue.get()),
    width=150
)
blendingButton.grid(row=8, column=1, padx=5, pady=(5, 10))

mediaButton = CTkButton(
    controlFrame, text="Média", 
    command=lambda: ApplyEffect("media", inputValue.get()),
    width=150
)
mediaButton.grid(row=9, column=0, padx=5, pady=(5, 10))

root.mainloop()