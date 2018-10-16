package com.test.savethemankind.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// TODO If we not releasing controller for Enemy, than this class must be static
public class MouseController extends MouseAdapter {
    private final int NUM_BUTTONS = 3;

    private final boolean[] buttons = new boolean[NUM_BUTTONS];
    private final boolean[] lastButtons = new boolean[NUM_BUTTONS];

    private int             x = -1;         // Текущее положение курсора по оси Х
    private int             y = -1;         // Текущее положение курсора по оси Y
    private int             last_x = -1;    // Предыдущее, последнее положение курсора по оси Х
    private int             last_y = -1;    // Предыдущее, последнее положение курсора по оси Y

    private boolean         moving;         // Двигается ли курсор мышки

    private boolean         disabled;       // Вкл/Выкл контроллера


    public int getX() { return x; }

    public int getY() { return y; }

    public boolean isMoving() { return moving; }

    @Override
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()-1] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()-1] = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Мышка должна принимать координаты в виде блоков, а не по пикселям
        x = e.getX();
        y = e.getY();
        moving = true;
    }

    public void update() {
        System.arraycopy(buttons, 0, lastButtons, 0, NUM_BUTTONS);

        // Определяем двигается ли мышка
        // TODO Проверить: нужно ли определять неподвижность мышки, например, в 5 сек (5х60 тактов)?
        if(x == last_x || y == last_y) {
            moving = false;
        }

        last_x = x;
        last_y = y;
    }

    public boolean isDown(int button) {
        // Фиксирует факт нажатия и удержание кнопки мыши (Кнопка нажата и удерживается)
        return buttons[button-1];
    }

    public boolean wasPressed(int button) {
        // Фиксирует факт нажатия кнопки мыши (Кнопка просто была нажата)
        return isDown(button) && !lastButtons[button-1];
    }

    public boolean wasReleased(int button) {
        // Фиксирует факт отпускания кнопки мыши (Кнопка, после нажатия, отпущена)
        return !isDown(button) && lastButtons[button-1];
    }

    public void turnOff() {
        disabled = true;
    }

    public void turnOn() {
        disabled = false;
    }

    public boolean isDisabled() {
        return disabled;
    }
}
