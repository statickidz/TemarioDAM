from Model.Insecto.Antena import Antena
from Model.Insecto.Ojo import Ojo
from Model.Insecto.Pelo import Pelo

class Insecto():
    color = ""
    tamanio = ""
    aspecto = ""
    antenas = Antena() # propiedad compuesta por el objeto objeto Antena
    ojos = Ojo()       # propiedad compuesta por el objeto objeto Ojo
    pelos = Pelo()     # propiedad compuesta por el objeto objeto Pelo