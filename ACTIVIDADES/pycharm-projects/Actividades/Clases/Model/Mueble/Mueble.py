class Mueble:
    def __init__(self, tipo):
        self.tipo = tipo
        self.medidas = ()
    def setTipo(self, tipo):
        self.tipo = tipo
    def getTipo(self):
        return self.tipo
    def addMedida(self, medida):
        self.medidas = self.medidas + (medida,)
        return self
    def getMedidas(self):
        return self.medidas