from Model.Mueble.Mueble import Mueble

class Mesa(Mueble):
    def __init__(self, tipo, color):
        Mueble.__init__(self, tipo)
        self.color = color
        self.medidas = list()
    def setColor(self, color):
        self.color = color
    def getColor(self):
        return self.color
    def addMedida(self, medida):
        self.medidas.append(medida)
        return self
    def getMedidas(self):
        return self.medidas