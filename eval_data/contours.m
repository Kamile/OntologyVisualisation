M = dlmread('contours.txt');
disp(M);
axis([0 5 0 15])
set(gca, 'XTick', 0:5)
hold on;
plot(0,0);
title('Performance for increasing contour number');
xlabel('Number of contours');
ylabel('Visualisation time(ms)');
for i = 1:length(M(1,:))
    errorbar(i, mean(M(:,i)), std(M(:,i)), '.g');
    plot(i, mean(M(:,i)), '*');
end
hold off;