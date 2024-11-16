import speech_recognition as sr
import pyautogui
import time
import tkinter as tk
import threading

RED = "\033[31m"
RESET = "\033[0m"
YELLOW = "\033[33m"
GREEN = "\033[32m"

class VoicePrintApp:
    def __init__(self, root):
        self.root = root
        self.root.title("Voice Print")
        self.prints_made = 0
        self.is_running = True  # Flag to stop the thread
        """To be set according to the calculation speed of the computer in use."""
        self.key_pause = 0.5  # Default pause value

        self.label = tk.Label(root, text=f"Prints made: {self.prints_made}", font=("Arial", 14))
        self.label.pack(pady=10)

        self.close_button = tk.Button(root, text="Exit", command=self.terminate_program, font=("Arial", 12))
        self.close_button.pack(pady=10)

        self.pause_label = tk.Label(root, text="Pause between keys (seconds):", font=("Arial", 12))
        self.pause_label.pack(pady=10)
        """pause from 0.1 to 2.0 sec"""
        self.pause_scale = tk.Scale(root, from_=0.1, to=2.0, resolution=0.1, orient="horizontal", length=300,
                                    command=self.update_pause)
        self.pause_scale.set(self.key_pause)
        self.pause_scale.pack(pady=10)

        # Start the voice recognition thread
        self.voice_thread = threading.Thread(target=self.run_voice_recognition)
        self.voice_thread.start()

    def update_prints(self):
        self.prints_made += 1
        self.label.config(text=f"Prints made: {self.prints_made}")

    def update_pause(self, value):
        self.key_pause = float(value)

    def terminate_program(self):
        self.is_running = False
        self.root.quit()
        print(f"{RED}Sto terminando il programma...{RESET}")


    def run_voice_recognition(self):
        print("Say 'stampa' to trigger printing, or 'esci' to terminate.")
        recognizer = sr.Recognizer()
        while self.is_running:
            with sr.Microphone() as source:
                print(f"{YELLOW}Listening...{RESET}")
                audio = recognizer.listen(source)

            try:
                command = recognizer.recognize_google(audio, language="it-IT").lower()
                print(f"You said: {command}")

                if "stampa" in command:
                    print(f"{GREEN}Ctrl+P+Enter combination sent.{RESET}")
                    trigger_print_shortcut(self.key_pause)
                    self.update_prints()
                elif "esci" in command:
                    self.terminate_program()
                    break

            except sr.UnknownValueError:
                print("I couldn't understand. Please try again.")
            except sr.RequestError:
                print("Error with the speech recognition service.")


def trigger_print_shortcut(pause_time):
    pyautogui.hotkey("ctrl", "p")
    time.sleep(pause_time)
    print(f"{GREEN}Stampa in corso...{RESET}")
    pyautogui.press("enter")


if __name__ == "__main__":

    root = tk.Tk()
    app = VoicePrintApp(root)
    root.protocol("WM_DELETE_WINDOW", app.terminate_program)
    root.mainloop()

    app.voice_thread.join()
