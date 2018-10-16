package com.test.savethemankind.bin;

import com.test.savethemankind.entities.Player;
import com.test.savethemankind.entities.Unit;
import com.test.savethemankind.graphics.Sprite;
import com.test.savethemankind.input.MouseController;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

public class Application extends Canvas implements Runnable {
    private static final String TITLE  = "To Save The Mankind";
    private static final int    WIDTH  = 640;
    private static final int    HEIGHT = 640;

//    private static MainThread mainThread;   // Главный тред игры
    /** Наша скорость в мс = 10*/
    private static final double  TARGET          = 60.0;                   // Максимальный fps, tps (желаемый)
    private static final int     MAX_FRAME_SKIPS = 5;                      // Максимальное число пропускаемых кадров
    private static final double  FRAME_PERIOD    = 1000000000.0 / TARGET;  // Период кадра
    private static final double  TICK_PERIOD     = 1000000000.0 / TARGET;  // Период такта между кадрами
    private boolean              running         = false;

    private static Player        testPlayer;
    private static Player        testEnemy;

    private static Sprite        basicPlate;
    private static Sprite        playerTank;
    private static Sprite        enemyTank;

    private void init() {
        // ----> Resource initing
        playerTank = new Sprite("tank_composed.png");
        enemyTank = new Sprite("e_tank_composed.png");
        basicPlate = new Sprite("foundation_basic_plate.png");

        // TODO I think after Faction must be Manager Class. After Manager - Player and Enemy classes
        // ----> Player initing (with Tank)
        // TODO Maybe it will move to other classes
        Unit testPlayerUnit = new Unit(playerTank, 64*7+2, 64*8-18, 0);
        Unit[] testPlayerUnits = new Unit[1];
        testPlayerUnits[0] = testPlayerUnit;

        testPlayer = new Player("Toughie", testPlayerUnits);

        // ----> Enemy initing (with Tank)
        // TODO Maybe it will move to other classes
        Unit testEnemyUnit = new Unit(enemyTank, 64*2+2, 64-18, 0);
        Unit[] testEnemyUnits = new Unit[1];
        testEnemyUnits[0] = testEnemyUnit;

        testEnemy = new Player("JavaBot", testEnemyUnits);
        testEnemy.disablingControllers();

        // ----> Controls initing
        // TODO Perhaps this listeners need to move in Player/Faction class
        addMouseListener(testPlayer.getMouseController());
        addMouseMotionListener(testPlayer.getMouseController());    // Для отслеживания перемещений

        // ----> Thread initing
//        mainThread = new MainThread(this);
    }

    private void tick() {
        testPlayer.tick();
        testEnemy.tick();
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            requestFocus();
            return;
        }

        Graphics graphics = bs.getDrawGraphics();

        /* -------------------------- Picture drawing ------------------------------------------- */
        graphics.setColor(Color.CYAN);
        graphics.fillRect(0,0, WIDTH, HEIGHT);

        // FIXME Добавить проверку по ширине спрайта
        // FIXME Всё это должно быть в классе GameMap
        // Рисуем карту 10х10
        for (int x = 0; x < WIDTH; x += 64) {
            for (int y = 0; y < HEIGHT; y += 64) {
                basicPlate.render(graphics, x, y);
            }
        }

        // FIXME Добавить метод, который рисует спрайт относительно блока правильно
        // FIXME Rendering Sprite in Unit class
        // Рисуем спрайты танков. Танк игрока в блоке (7, 8). Танк врага в блоке (2, 1)
        // Картинка танка имеет размер 60х101. Значит его центр - точка (30, 50,5)
        // Плитка имеет размер 64х64 и её центр в точке (32, 32)
        // То есть танк надо сместить по х на +2, по Y на -18
        // В идеале, центр танка должен быть в центре его тела, без учёта пушки
        testPlayer.render(graphics);
        testEnemy.render(graphics);

        /* -------------------------------------------------------------------------------------- */

        graphics.dispose();
        bs.show();
    }

    private void update(long delta) {

    }

    @Override
    public void run() {
        /* Это - thread.run() */

        // Последнее рассчитанное время начала цикла Треда
        long last_time = System.nanoTime();

        // Таймер в миллисекундах
        long timer = System.currentTimeMillis();

        // Время работы цикла Треда вхолостую (без процесса)
        double unprocessed = 0.0;

        // Частота кадра в секунду и такта между кадрами
        int fps = 0;
        int tps = 0;

        // Могу ли рендерить(рисовать) кадр
        boolean can_render;

        // Инициализируем все ресурсы игры
        //FIXME вероятно, что вызов метода может быть и не здесь
        init();

        while(running) {
            // Текущее время (начало цикла)
            long now_time = System.nanoTime();

            unprocessed += (now_time - last_time) / TICK_PERIOD;

            last_time = now_time;

            /* Если время простоя цикла больше или равно периоду такта между кадрами,
               то вызываем метод обработки такта tick() */
            if (unprocessed >= 1.0){
                this.tick();
                // TODO KeyController.update()
                testPlayer.getMouseController().update();

                unprocessed--;
                tps++;

                // Можно приступать к рендерингу кадра
                can_render = true;

            } else {
                can_render = false;
            }

            //
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (can_render) {
                this.render();
                fps++;
            }

            // Здесь просто проверяем успели ли мы срендерить кадр (1 секунда)
            if (System.currentTimeMillis() - 1000 > timer) {
                timer += 1000;

                System.out.printf("FPS: %d | TPS: %d\n", fps, tps);

                // Сбрасываем значения fps и tps
                fps = 0;
                tps = 0;
            }
        }

        // Выход из приложения
        System.exit(0);
    }

    private void start() {
        // FIXME Вместо этого будет наш алгоритм мультитрединга
        if(running) return;
        running = true;

        new Thread(this, "SaveTheMankindMain-Thread").start();
    }

    private void stop() {
        if(!running) return;
        running = false;
    }

//     const int blockSize // pixels (dps on Android)
//     deathDuration

//     There will be also constants for:
//        - mass, calibr, shootSpeed etc. for each weapon type
//        - mass, HP, speed etc. for each unit type
//        - error codes

//     int worldSpeed // the frequency of the game world(map) update, actually it is the main "game speed" in milliseconds
//
//     int gameSpeed // -5..5?

//     Let consider it as a "user customized" game speed or a "user-defined game speed increment/decrement".
//     The user is supposed to be able to increase and decrease the game speed using some hotkeys.
//     This feature has the following implications:
//        - min and max
//     It seems to be hard to programmatically detect the minimal and maximal scale points of the
//     game speed, because everything depend on the average speed of construction and movement.
//     Only a live user has a feeling about the game speed. Probably, it is better to consider
//     the default game speed as a 0 point and give the possibility to make it let us say 5 times
//     slower and 5 times faster.
//        - which parameters regulate it
//     There seem to be two ways of implementation:
//     1. To change map recalculation time (worldSpeed). Effect: By decreasing of the worldSpeed
//        the "frames" or the game world update faster, more frequent calculation requires more computation resources.
//        By incresing of the worldSpeed the frames go more sparsed (no jumping effect - see "2" below) - no problem.
//     2. To increase the "speed" parameter of all units (the building construct/upgrade faster,
//        the units move/shoot faster). Effect: By the unit speed increasing the picture "jump" effect takes effect (too much things happen between two map frames). By the unit speed decreasing - no problem.
//        It is a hard decision which way is better. Let implement both approaches with a possibility
//        to switch them using a parameter.
//            - affects everything or only construction/upgrade
//     If the approach 2 (see "two ways of implementation" above) is used then it is possible to
//     fast/slow only construction processes and not moving/shooting.
//     Maybe we should also make it configurable and test both.

//     calcWorld() // global function which moves all objects at each worldSpeed milliseconds
//     {
//           // calculation order
//           1. Recalculation of HP:
//            - damage caused by explosion to the near located objects (this step is skipped on the
//              top level of recursion, see below "Recursive calculaion of the damage caused by
//              explosion to the near located objects")
//            - damage caused by burning status of the object (or start of burning)
//            - movement of bullets (including out-shooting) and hit of bullets, causing damage to objects (minusing HP)
//            - increasion of HP due to building process
//            - increasion of HP by repairment (ongoing HP repairment)
//            - death of objects with/without explosion due to the bullet hit or a burning status
//
//           Recursive calculaion of the damage caused by explosion to the near located objects:
//             Since the explosion sequence in the real world is a highly-complicated calculation,
//             we use the following model. Let assume that:
//            - All explosions complete within one calculation step (time unit), so at each
//              calculation step we should not think about consequences of explosion fron the previous calculation step;
//            - On the first recursion level we perform calculation using the above HP recalculation steps without (*)-step.
//            - At each calculation step we iterate all objects on a loop sequentially without giving priority to some objects.
//              After all HP decrements we detect a subset of objects that were exploded as a result
//              of the _first_recursion_step_ of the current calculation step.
//              If they were we iterate through the subset of exploded objects and calculate the
//              subset of objects affected by the burst.
//              On the 2nd recursion step we apply the above HP recalculation steps again,
//              but only for the subset of affected objects.
//              If some of them were exploded in turn due to HP decrement then we initiate the 3rd
//              round and so on, until the subset of affected objects gets empty.
//
//           After all the above actions are calculated for all objects we finally decide
//           which of them start diing and which are died (were destroyed and deathDuration has gone).
//
//           2. Planned/Ongoing processes.
//           - cancelling of build/upgrade/repair if:
//             - the corresponding factory/engineer has been destroyed (this is calculated above)
//             - the object itself was destroyed (this is calculated above)
//           - recalculation of resources (money/energy etc.):
//             - increment resources by checking of all harvesting processes
//             - decrement resources by checking all consuming processes
//             - suspend build/upgrade/repair if not enough resource to continue building
//             NOTE: If some resource gets 0 and several construction works must be suspended due
//                   that then we share the last portion of the resource equally for all
//           - creation of new units, start of building (ground allocation),
//             upgrade status update, transformation of the objects/units into a new form due to upgrade, but only in case if:
//             - there was no damage to that point on the current time unit, otherwise the
//               engineer must die and no building process should start
//             - there is enough resources (if the user managed to order more than one new
//               building within time unit then it is possible that part of them are accepted and
//               part of them are declined when there are resources only for a part of them)
//
//           3. Movement of all units according their targets.
//           Target is not always an enemy. It could be a building for engineer to support
//           building/repair, or just a point on the map.
//           - move all units which already have a target (check if the target still exist)
//           - start moving all units which detected an enemy within the detectable radius
//           NOTE 1: handle properly concurrent movements! If several objects want to stop on the
//                   same map block at the same time, only one of them should be allowed to do it.
//           The priority rules should be formulated a) within players, b) within objects of the same player
//           After the object is moved the "object" fields of the Map class should be updated accordingly.
//           NOTE 2: If two objects don't stop, but pass through the map block at the same time
//                   then on the alpha implementation phase we could accept vizualization of on
//                   unit on another one, since the calculation and vizualization of bypassing way is a very hard task.
//
//           4. Update of the sprites, colors, burning, explosion(diing).
//     }

//     boolean targetShootable(x,y,z,weapon) // checks if it is possible to shoot the point (x,y,z)
//                                           // using the weapon (weapon). These will be some mechanics
//                                           // formulae inside this function.

    public static void main(String[] args) {
        Application game = new Application();

        // Определяем размер приложения
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // Создаём окно, в котором будет запускаться игра
        JFrame frame = new JFrame(TITLE);
        frame.setLayout(new BorderLayout());
        frame.add(game, BorderLayout.CENTER);     // Добавляем холст на наш фрейм
        frame.pack();                             // Сворачиваем окно до размера приложения
        frame.setResizable(false);
        frame.setFocusable(true);

        // Лучше написать слушателя событий, который может контролировать приложение
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                /* Для закрытия окна будем использовать наши методы класса */
                System.err.println("Exiting game");
                game.stop();
            }
        });

        frame.setLocationRelativeTo(null);        // Установка окна в центр экрана
        frame.setVisible(true);
        frame.requestFocus();                     // Установка фокуса для контроллеров игры

        game.start();
    }
}
