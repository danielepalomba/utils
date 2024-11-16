import speech_recognition as sr
import pyautogui
import time
import tkinter as tk
import threading


class StampaVocaleApp:
    def __init__(self, root):
        self.root = root
        self.root.title("Stampa Vocale")
        self.stampe_effettuate = 0
        self.is_running = True  # Flag per terminare il thread

        self.label = tk.Label(root, text=f"Stampe effettuate: {self.stampe_effettuate}", font=("Arial", 14))
        self.label.pack(pady=10)

        self.close_button = tk.Button(root, text="Esci", command=self.termina_programma, font=("Arial", 12))
        self.close_button.pack(pady=10)

        # Avvia il thread per il riconoscimento vocale
        self.voice_thread = threading.Thread(target=self.run_voice_recognition)
        self.voice_thread.start()

    def aggiorna_stampe(self):
        """Aggiorna il numero di stampe nella finestra."""
        self.stampe_effettuate += 1
        self.label.config(text=f"Stampe effettuate: {self.stampe_effettuate}")

    def termina_programma(self):
        """Termina l'applicazione e chiude la finestra."""
        self.is_running = False
        self.root.quit()

    def run_voice_recognition(self):
        """Loop per il riconoscimento vocale in esecuzione su un thread separato."""
        print("Pronuncia 'stampa' per attivare la stampa, o 'esci' per terminare.")
        recognizer = sr.Recognizer()
        while self.is_running:
            with sr.Microphone() as source:
                print("Parla ora...")
                audio = recognizer.listen(source)

            try:
                command = recognizer.recognize_google(audio, language="it-IT").lower()
                print(f"Hai detto: {command}")

                if "stampa" in command:
                    print("Combinazione Ctrl+P+Enter inviata.")
                    trigger_print_shortcut()
                    self.aggiorna_stampe()
                elif "esci" in command:
                    print("Terminazione del programma.")
                    self.termina_programma()
                    break

            except sr.UnknownValueError:
                print("Non ho capito. Riprova.")
            except sr.RequestError:
                print("Errore nel servizio di riconoscimento vocale.")


def trigger_print_shortcut():
    """Simula la combinazione di tasti Ctrl+P e Enter per stampare.
       N.B. LA COMBINAZIONE DI TASTI DIPENDE DALLA STAMPANTE IN USO    
    """
    pyautogui.hotkey("ctrl", "p")
    time.sleep(0.5)
    pyautogui.press("enter")


if __name__ == "__main__":

    root = tk.Tk()
    app = StampaVocaleApp(root)

    root.mainloop()

    # Attende la fine del thread vocale prima di uscire completamente
    app.voice_thread.join()
