package year2017.puzzle20;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Part A: 150
 * Part B: 657
 */
public class Puzzle20 {
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2017/input20.txt");

        partA(input);
        partB(input);
    }

    private static void partB(List<String> input) {
        List<Particle> particles = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            particles.add(new Particle(i, input.get(i)));
        }

        int ticksWithoutCollisions = 0;
        while (ticksWithoutCollisions < 1000 && particles.size() > 1) {
            particles.forEach(Particle::tick);

            List<Particle> particlesToRemove = new ArrayList<>();
            for (int i = 0; i < particles.size() - 1; i++) {
                for (int j = i + 1; j < particles.size(); j++) {
                    if (particles.get(i).isColliding(particles.get(j))) {
                        particlesToRemove.add(particles.get(i));
                        particlesToRemove.add(particles.get(j));
                    }
                }
            }

            particles.removeAll(particlesToRemove);

            if (particlesToRemove.isEmpty()) {
                ticksWithoutCollisions++;
            } else {
                ticksWithoutCollisions = 0;
            }
        }

        System.out.println("Part B: " + particles.size());
    }

    private static void partA(List<String> input) {
        Vector3D zeroPoint = new Vector3D(0, 0, 0);

        List<Particle> particles = new ArrayList<>();
        long minDistanceToOrigin = Long.MAX_VALUE;
        Particle closestParticle = null;
        for (int i = 0; i < input.size(); i++) {
            Particle particle = new Particle(i, input.get(i));
            particles.add(particle);

            if (particle.getDistance(zeroPoint) < minDistanceToOrigin) {
                minDistanceToOrigin = particle.getDistance(zeroPoint);
                closestParticle = particle;
            }
        }

        long numberOfTimesClosest = 0;
        while (numberOfTimesClosest < 3000) {
            numberOfTimesClosest++;
            Particle newClosestParticle = particles.getFirst();
            minDistanceToOrigin = Long.MAX_VALUE;
            for (Particle particle : particles) {
                particle.tick();
                if (particle.getDistance(zeroPoint) < minDistanceToOrigin) {
                    minDistanceToOrigin = particle.getDistance(zeroPoint);
                    newClosestParticle = particle;
                }
            }
            if (!newClosestParticle.equals(closestParticle)) {
                closestParticle = newClosestParticle;
                numberOfTimesClosest = 0;
            }
        }

        System.out.println("Part A: " + closestParticle.getId());
    }
}
